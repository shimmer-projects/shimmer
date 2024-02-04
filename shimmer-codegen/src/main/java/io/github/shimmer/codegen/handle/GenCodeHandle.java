package io.github.shimmer.codegen.handle;

import io.github.shimmer.codegen.entity.GenColumn;
import io.github.shimmer.codegen.entity.GenModel;
import io.github.shimmer.codegen.entity.GenModule;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>模板渲染处理</p>
 * Created on 2023-02-02 16:08
 *
 * @author yu_haiyang
 */

@Component
public class GenCodeHandle {

    private static final String MODULE = "$module$";
    private static final String MODEL = "$Model$";
    private static final String PACKAGE = "$package$";
    private static final String SEPARATOR = "/";
    private static final String POINT = ".";

    private final VelocityEngine engine;

    //private final VelocityContext context;


    public GenCodeHandle() {
        //设置velocity资源加载器
        Properties properties = new Properties();
        properties.put("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.put("resource.default_encoding", "UTF-8");
        properties.put("output.encoding", "UTF-8");
        Velocity.init(properties);
        //实例化一个VelocityEngine对象
        engine = new VelocityEngine(properties);
    }

    public byte[] handle() throws IOException {

        // 定义字段
        GenColumn column1 = new GenColumn();
        column1.setColumnName("url");
        column1.setComments("菜单的URL");
        column1.setNullable(true);
        GenColumn column2 = new GenColumn();
        column2.setColumnName("method");
        column2.setComments("请求方法");

        //定义模型
        GenModel model = new GenModel();
        model.setColumns(Arrays.asList(column1, column2));
        model.setModelName("Menu");
        model.setModelDescription("菜单模型");

        GenModel model1 = new GenModel();
        model1.setColumns(Arrays.asList(column1, column2));
        model1.setModelName("Resource");
        model1.setModelDescription("资源管理");

        List<GenModel> modelList = List.of(model, model1);

        // 定义模块
        GenModule module = new GenModule();
        module.setModuleName("cheer-menu");
        module.setAuthor("YIHE");
        module.setPackageName("com.example");
        module.setModels(modelList);


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //FileOutputStream outputStream = new FileOutputStream(module.getModuleName() + ".zip");
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        //创建Velocity容器
        VelocityContext context = new VelocityContext();
        // 函数库
        context.put("math", new MathTool());
        context.put("dateTool", new DateTool());

        context.put("description", "这是表述信息");
        context.put("datetime", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));

        List<String> templates = getModelTemplates();
        for (GenModel genModel : modelList) {
            context.put("module", module);
            context.put("model", genModel);
            // 对每一个模型都编译一套模板
            for (String template : templates) {
                //加载模板
                Template tpl = engine.getTemplate(template, "UTF-8");
                StringWriter sw = new StringWriter();
                tpl.merge(context, sw);
                String render = sw.toString();
                String dest = getFileName(template, module.getModuleName(), genModel.getModelName(), module.getPackageName());
                zip.putNextEntry(new ZipEntry(dest));
                zip.write(render.getBytes(StandardCharsets.UTF_8));
                zip.closeEntry();
                sw.close();
                System.out.println(dest);
            }
        }

        List<String> commonTemplates = getCommonTemplates();
        for (String template : commonTemplates) {
            //加载模板
            Template tpl = engine.getTemplate(template, "UTF-8");
            StringWriter sw = new StringWriter();
            tpl.merge(context, sw);
            String render = sw.toString();
            String dest = getFileName(template, module.getModuleName(), "", module.getPackageName());
            zip.putNextEntry(new ZipEntry(dest));
            zip.write(render.getBytes(StandardCharsets.UTF_8));
            zip.closeEntry();
            sw.close();
            System.out.println(dest);
        }

        zip.close();
        outputStream.flush();//强制刷新出去
        outputStream.close();

        return outputStream.toByteArray();
    }

    public List<String> getModelTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("velocity/$module$/src/main/java/$package$/controller/$Model$Controller.java.vm");
        templates.add("velocity/$module$/src/main/java/$package$/entity/$Model$Entity.java.vm");
        templates.add("velocity/$module$/src/main/java/$package$/mapper/$Model$Mapper.java.vm");
        templates.add("velocity/$module$/src/main/java/$package$/repository/$Model$Repository.java.vm");
        templates.add("velocity/$module$/src/main/java/$package$/request/$Model$Request.java.vm");
        templates.add("velocity/$module$/src/main/java/$package$/response/$Model$Response.java.vm");
        templates.add("velocity/$module$/src/main/java/$package$/service/$Model$Service.java.vm");
        templates.add("velocity/$module$/src/main/java/$package$/service/impl/$Model$ServiceImpl.java.vm");
        return templates;
    }

    public List<String> getCommonTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("velocity/$module$/src/main/resources/application.yml.vm");
        templates.add("velocity/$module$/src/main/resources/logback-spring.xml.vm");
        templates.add("velocity/$module$/pom.xml.vm");
        return templates;
    }

    private String getFileName(String template, String module, String model, String basePackage) {
        if (Objects.isNull(template)) {
            throw new RuntimeException("template path must be not null!");
        }
        String path = template
                .substring(template.indexOf(SEPARATOR) + 1, template.lastIndexOf(POINT))
                .replace(SEPARATOR, File.separator);
        basePackage = basePackage.replace(POINT, File.separator);
        String finalPath = path.replace(MODULE, module).replace(MODEL, model).replace(PACKAGE, basePackage);
        return finalPath;
    }

}
