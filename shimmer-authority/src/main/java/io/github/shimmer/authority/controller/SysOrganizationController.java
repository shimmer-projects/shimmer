package io.github.shimmer.authority.controller;

import io.github.shimmer.authority.request.SysOrganizationRequest;
import io.github.shimmer.authority.response.SysOrganizationResponse;
import io.github.shimmer.authority.service.SysOrganizationService;
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

import java.util.List;


/**
 * <p>组织机构管理</p>
 *
 * @author yu_haiyang
 */
@Rest(path = "org", name = "组织管理", description = "组织机构相关的api接口")
public class SysOrganizationController {
    @Resource
    private SysOrganizationService sysOrganizationService;


    /**
     * 添加组织
     *
     * @param request 传递过来的组织基本信息
     * @return 添加完之后的组织ID
     */
    @Post(path = "/insert", name = "新增组织", description = "用于添加组织，该接口需要登录后并拥有该接口的访问权限")
    @Debounce
    public ApiResult insert(
            @Validated(VG.C.class) @RequestBody SysOrganizationRequest request) {
        Long id = sysOrganizationService.insert(request);
        return ApiResult.ok(id);
    }


    /**
     * 根据组织的主键获取组织的信息
     *
     * @param id 组织的主键信息
     * @return void
     */
    @Delete(path = "delete", name = "删除组织", description = "根据组织ID进行对组织删除，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult delete(
            @Validated
            @NotNull(message = "组织ID不能为空")
            @DecimalMin(value = "1", message = "组织ID不能小于1")
            @Parameter(name = "id", description = "主键")
            @RequestParam Long id) {
        sysOrganizationService.delete(id);
        return ApiResult.ok(id);
    }


    /**
     * 组织修改
     *
     * @param request 修改后的组织信息
     * @return void
     */
    @Put(path = "update", name = "修改组织", description = "根据组织ID进行对组织进行修改，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult update(
            @Validated(VG.U.class) @RequestBody SysOrganizationRequest request) {
        sysOrganizationService.update(request);
        return ApiResult.ok();
    }


    /**
     * 根据组织的主键获取组织的信息
     *
     * @param id 组织的主键信息
     * @return 主键对应的组织详细信息
     */
    @Get(path = "detail", name = "查看组织详情", description = "根据组织ID查看组织的详细信息，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult detail(@NotNull(message = "组织ID不能为空")
                                                     @DecimalMin(value = "1", message = "组织ID不能小于1")
                                                     @Parameter(name = "id", description = "主键")
                                                     Long id) {
        SysOrganizationResponse detail = sysOrganizationService.detail(id);
        return ApiResult.ok(detail);
    }


    /**
     * 根据条件获取组织列表
     * 所有的条件注解属性的值为空的时候获取全量
     *
     * @param request 满足指定条件参数的组织信息
     * @return 满足条件的所有组织并分页
     */
    @Get(path = "fetch", name = "条件查询组织列表", description = "根据支持的条件查询字段查看组织列表信息，该接口需要登录后并拥有该接口的访问权限")
    public Pager fetch(Pager pager, SysOrganizationRequest request) {
        Pager fetch = sysOrganizationService.fetch(pager, request);
        return fetch;
    }

    /**
     * 根据条件获取组织列表
     * 所有的条件注解属性的值为空的时候获取全量
     *
     * @return 满足条件的所有组织并分页
     */
    @Get(path = "fetch-tree", name = "查询组织列表以树形结构", description = "查询组织列表以树形结构，该接口需要登录后并拥有该接口的访问权限")
    public List<SysOrganizationResponse> fetchTree() {
        List<SysOrganizationResponse> fetch = sysOrganizationService.fetchTree();
        return fetch;
    }
}
