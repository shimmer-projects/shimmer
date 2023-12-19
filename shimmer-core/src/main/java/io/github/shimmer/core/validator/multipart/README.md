# multipartFile-verify-annotation

#### 简介：

Spring boot 提供了优雅的参数校验方式，即使用 validation-api，关于 validation-api 内置的注解这里不作详细的解释，主要为以下几种：

| 注解                        | 说明                                                                          |
|---------------------------|-----------------------------------------------------------------------------|
| @Null                     | 限制只能为null                                                                   |
| @NotNull                  | 限制必须不为null                                                                  |
| @AssertFalse              | 限制必须为false                                                                  |
| @AssertTrue               | 限制必须为true                                                                   |
| @DecimalMax(value)        | 限制必须为一个不大于指定值的数字                                                            |
| @DecimalMin(value)        | 限制必须为一个不小于指定值的数字                                                            |
| @Digits(integer,fraction) | 限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction                           |
| @Future                   | 限制必须是一个将来的日期                                                                |
| @Max(value)               | 限制必须为一个不大于指定值的数字                                                            |
| @Min(value)               | 限制必须为一个不小于指定值的数字                                                            |
| @Past                     | 验证注解的元素值（日期类型）比当前时间早                                                        |
| @Pattern(value)           | 限制必须符合指定的正则表达式                                                              |
| @Size(max,min)            | 限制字符长度必须在min到max之间                                                          |
| @NotEmpty                 | 验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0)                                        |
| @NotBlank                 | 验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格 |
| @Email                    | 验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式                                 |

但有些时候，内置的并不能满足业务的需要，这里就介绍一下如何扩展 validation-api 实现自定义注解，以校验上传的文件类型为例子，如何实现自定义扩展的注解。

#### 一、注解定义

要实现扩展 validate 框架的注解比较简单，直接在注解上添加 @Constraint 并指定校验的实现类即可

```java

@Inherited
@Target({PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MultipartFilesValidator.class, MultipartFileValidator.class})
public @interface MultipartFileVerify {

    String message() default "文件校验失败";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 文件类型限制
     */
    FileType[] value() default {};

    /**
     * 不允许上传的文件类型，不指定则不作限制
     */
    FileType[] notAllow() default {};

    /**
     * 文件大小限制，小于 0 表示不作限制；单位：千字节（KB）
     */
    long maxSize() default -1L;
}
```

message、groups、payload 为必须项，其他的为自定义，该注解的只能用在 PARAMETER（方法参数）
上，并且为运行时可用，有两个校验类，分别为多文件 `MultipartFilesValidator` 与 单文件 `MultipartFileValidator` 校验实现。

#### 二、校验类实现

校验类必须实现 `ConstraintValidator` 接口

![image-20220409012014198](https://raw.githubusercontent.com/ZxbMsl160918/img-repository/master/img/image-20220409012014198.png)

泛型 A 表示自定义注解，T 表示你校验类需要传递的参数。

单文件校验类的实现

```java
/**
 * 单个 MultipartFile 校验
 */
public class MultipartFileValidator implements ConstraintValidator<MultipartFileVerify, MultipartFile> {
    @Resource
    private AbstractMultipartFileValidator abstractMultipartFileValidator;

    private MultipartFileVerify multipartFileValid;

    @Override
    public void initialize(MultipartFileVerify constraintAnnotation) {
        this.multipartFileValid = constraintAnnotation;
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return abstractMultipartFileValidator.isFileValid(multipartFileValid, value);
    }
}
```

多文件校验的实现

```java
/**
 * 兼容多 MultipartFile 校验
 */
public class MultipartFilesValidator implements ConstraintValidator<MultipartFileVerify, MultipartFile[]> {

    @Resource
    private AbstractMultipartFileValidator abstractMultipartFileValidator;

    private MultipartFileVerify multipartFileValid;

    @Override
    public void initialize(MultipartFileVerify constraintAnnotation) {
        this.multipartFileValid = constraintAnnotation;
    }

    @Override
    public boolean isValid(MultipartFile[] value, ConstraintValidatorContext context) {
        if (value == null || value.length == 0) {
            return true;
        }
        for (MultipartFile multipartFile : value) {
            if (!abstractMultipartFileValidator.isFileValid(multipartFileValid, multipartFile)) {
                return false;
            }
        }
        return true;
    }
}
```

写两个校验类是因为 ConstraintValidator 在校验时只能传一个参数呀，当同时上传多个文件就不能满足了，接口的方法参数类型必须与
ConstraintValidator T 泛型一致才行。这样就扩展出了一个文件校验的注解，AbstractMultipartFileValidator
这个抽象类作用是在责任链中校验传过来的文件格式是否一致。

#### 三、遇到的坑

1. 不能跟 Spring 一样去组合注解，什么意思呢？就是类似 @RestController 一样添加 @Controller 就能使用 @Controller 功能

   ![image-20220409014007662](https://raw.githubusercontent.com/ZxbMsl160918/img-repository/master/img/image-20220409014007662.png)

   类似这样，我想添加一个默认校验 Excel 类型的注解，这样子是做不到的，因为组合注解并非 Java 原始支持的，Spring 是自己实现的

   ```java
   @Inherited
   @Target({PARAMETER})
   @Retention(RUNTIME)
   @Documented
   @MultipartFileVerify(value = {FileType.XLS, FileType.XLSX})
   public @interface ExcelMultipartFile {
       String message() default "文件校验失败";
   
       Class<?>[] groups() default {};
   
       Class<? extends Payload>[] payload() default {};
   
       /**
        * 文件类型限制
        */
       @AliasFor(
               annotation = MultipartFileVerify.class
       )
       FileType[] value() default {};
   }
   ```

2. 定义与 ConstraintValidator<A extends Annotation, T>
   相同的实现类（两个泛型相同）会报错，因为底层会根据两个泛型查找实现类，然后与注册的实现类作对比，不是同一个类直接抛出异常，这个具体去看源码就知道了，这里不细说。

   例如上面的组合方法不行，于是我想通过额外定义一个校验类去解决，心想不就再定义多两个类嘛，反正校验逻辑也是同样的，可当我开开心心撸完代码想测试一下，发现并不行；类似这样子：

   ```java
   
   @Inherited
   @Target({PARAMETER})
   @Retention(RUNTIME)
   @Documented
   @Constraint(validatedBy = {ExcelMultipartFileValidator.class})
   public @interface ExcelMultipartFile {
       String message() default "文件校验失败";
   
       Class<?>[] groups() default {};
   
       Class<? extends Payload>[] payload() default {};
   
       /**
        * 文件类型限制
        */
       @AliasFor(
               annotation = MultipartFileVerify.class
       )
       FileType[] value() default {};
   }
   
   
   /**
    * 单个 ExcelMultipartFile 校验
    */
   class ExcelMultipartFileValidator implements ConstraintValidator<MultipartFileVerify, MultipartFile> {
       @Resource
       private AbstractMultipartFileValidator abstractMultipartFileValidator;
   
       private MultipartFileVerify multipartFileValid;
   
       @Override
       public void initialize(MultipartFileVerify constraintAnnotation) {
           this.multipartFileValid = constraintAnnotation;
       }
   
       @Override
       public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
           if (value == null) {
               return true;
           }
           return abstractMultipartFileValidator.isFileValid(multipartFileValid, value);
       }
   }
   ```

   可能有些小伙伴就问了，干嘛不直接拿 `MultipartFileValidator` 这个校验类用在 Excel
   校验注解上？很遗憾这样也不行，后面这个怎么实现组合注解有时间再去研究下，目前这样子已经符合需求了。

#### 四、源码

上面的完整代码：[戳我打开](https://gitee.com/zxbmsl/multipart-file-verify-annotation)

