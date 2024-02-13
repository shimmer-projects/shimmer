package io.github.shimmer.authority.controller;

import io.github.shimmer.authority.request.AuthorizeMenuRequest;
import io.github.shimmer.authority.request.PermitDataRequest;
import io.github.shimmer.authority.request.SysRoleRequest;
import io.github.shimmer.authority.response.SysRoleResponse;
import io.github.shimmer.authority.service.SysRoleService;
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
 * <p>角色管理</p>
 *
 * @author yu_haiyang
 */

@Rest(path = "role", name = "角色管理", description = "角色管理相关的api接口")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 添加角色
     *
     * @param request 传递过来的角色基本信息
     * @return 添加完之后的角色ID
     */
    @Post(path = "/insert", name = "新增角色", description = "用于添加角色，该接口需要登录后并拥有该接口的访问权限")
    @Debounce
    public ApiResult insert(@Validated(VG.C.class) @RequestBody SysRoleRequest request) {
        Long id = sysRoleService.insert(request);
        return ApiResult.ok(id);
    }

    /**
     * 根据角色的主键获取角色的信息
     *
     * @param id 角色的主键信息
     * @return void
     */
    @Delete(path = "delete", name = "删除角色", description = "根据角色ID进行对角色删除，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult delete(@Validated
                                  @NotNull(message = "角色ID不能为空")
                                  @DecimalMin(value = "1", message = "角色ID不能小于1")
                                  @Parameter(name = "id", description = "主键")
                                  @RequestParam Long id) {
        sysRoleService.delete(id);
        return ApiResult.ok(id);
    }

    /**
     * 角色修改
     *
     * @param request 修改后的角色信息
     * @return void
     */
    @Put(path = "update", name = "修改角色", description = "根据角色ID进行对角色进行修改，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult update(@Validated(VG.U.class) @RequestBody SysRoleRequest request) {
        sysRoleService.update(request);
        return ApiResult.ok();
    }

    /**
     * 根据角色的主键获取角色的信息
     *
     * @param id 角色的主键信息
     * @return 主键对应的角色详细信息
     */
    @Get(path = "detail", name = "查看角色详情", description = "根据角色ID查看角色的详细信息，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult detail(@NotNull(message = "角色ID不能为空")
                                             @DecimalMin(value = "1", message = "角色ID不能小于1")
                                             @Parameter(name = "id", description = "主键")
                                             @RequestParam Long id) {
        SysRoleResponse detail = sysRoleService.detail(id);
        return ApiResult.ok(detail);
    }

    /**
     * 根据条件获取角色列表
     * 所有的条件注解属性的值为空的时候获取全量
     *
     * @param request 满足指定条件参数的角色信息
     * @return 满足条件的所有角色并分页
     */
    @Get(path = "fetch", name = "条件查询角色列表", description = "根据支持的条件查询字段查看角色列表信息，该接口需要登录后并拥有该接口的访问权限")
    public Pager fetch(Pager pager, SysRoleRequest request) {
        Pager fetch = sysRoleService.fetch(pager, request);
        return fetch;
    }

    @Put(path = "assign-user", name = "为角色分配用户", description = "为角色分配拥有该角色的用户,该接口需要登录后并拥有该接口的访问权限")
    public void assignUser() {
        // TODO do anything
    }

    /**
     * 批准授权的访问菜单
     */
    @Put(path = "authorize-menu", name = "批准授权访问菜单", description = "为角色分配可以访问的菜单,该接口需要登录后并拥有该接口的访问权限")
    public void authorizeMenu(@Validated AuthorizeMenuRequest request) {
        // TODO do anything
    }

    /**
     * 许可访问数据
     */
    @Put(path = "permit-data", name = "许可的访问数据", description = "为角色分配可以访问的数据,该接口需要登录后并拥有该接口的访问权限")
    public void permitData(@Validated PermitDataRequest request) {
        // TODO do anything
    }
}
