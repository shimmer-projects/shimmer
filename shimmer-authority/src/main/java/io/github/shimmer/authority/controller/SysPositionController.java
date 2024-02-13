package io.github.shimmer.authority.controller;

import io.github.shimmer.authority.request.SysPositionFetchRequest;
import io.github.shimmer.authority.request.SysPositionRequest;
import io.github.shimmer.authority.response.SysPositionResponse;
import io.github.shimmer.authority.service.SysPositionService;
import io.github.shimmer.core.annotation.*;
import io.github.shimmer.core.debounce.Debounce;
import io.github.shimmer.core.response.data.ApiResult;
import io.github.shimmer.core.response.data.Pager;
import io.github.shimmer.core.validator.VG;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * <p>职位管理</p>
 *
 * @author yu_haiyang
 */
@Rest(path = "position", name = "职位管理", description = "职位相关的api接口")
public class SysPositionController {

    @Resource
    private SysPositionService sysPositionService;

    /**
     * 添加职位
     *
     * @param request 传递过来的职位基本信息
     * @return 添加完之后的职位ID
     */
    @Post(path = "/insert", name = "新增职位", description = "用于添加职位，该接口需要登录后并拥有该接口的访问权限")
    @Debounce
    public ApiResult insert(@Validated(VG.C.class) @RequestBody SysPositionRequest request) {
        Long id = sysPositionService.insert(request);
        return ApiResult.ok(id);
    }


    /**
     * 根据职位的主键获取职位的信息
     *
     * @param id 职位的主键信息
     * @return void
     */
    @Delete(path = "delete", name = "删除职位", description = "根据职位ID进行对职位删除，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult delete(@Validated
                                  @NotNull(message = "职位ID不能为空")
                                  @DecimalMin(value = "1", message = "职位ID不能小于1")
                                  @Parameter(name = "id", description = "主键")
                                  @RequestParam Long id) {
        sysPositionService.delete(id);
        return ApiResult.ok(id);
    }


    /**
     * 职位修改
     *
     * @param request 修改后的职位信息
     * @return void
     */
    @Put(path = "update", name = "修改职位", description = "根据职位ID进行对职位进行修改，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult update(@Validated(VG.U.class) @RequestBody SysPositionRequest request) {
        sysPositionService.update(request);
        return ApiResult.ok(request.getId());
    }


    /**
     * 根据职位的主键获取职位的信息
     *
     * @param id 职位的主键信息
     * @return 主键对应的职位详细信息
     */
    @Get(path = "detail", name = "查看职位详情", description = "根据职位ID查看职位的详细信息，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult detail(@NotNull(message = "职位ID不能为空")
                                                 @DecimalMin(value = "1", message = "职位ID不能小于1")
                                                 @Parameter(name = "id", description = "主键")
                                                 @RequestParam Long id) {
        SysPositionResponse detail = sysPositionService.detail(id);
        return ApiResult.ok(detail);
    }


    /**
     * 根据条件获取职位列表
     * 所有的条件注解属性的值为空的时候获取全量
     *
     * @param request 满足指定条件参数的职位信息
     * @return 满足条件的所有职位并分页
     */
    @Get(path = "fetch", name = "条件查询职位列表", description = "根据支持的条件查询字段查看职位列表信息，该接口需要登录后并拥有该接口的访问权限")
    @Debounce
    public Pager fetch(SysPositionFetchRequest request) {
        Pager fetch = sysPositionService.fetch(request);
        return fetch;
    }

}
