package io.github.shimmer.authority.controller;

import io.github.shimmer.authority.request.SysMenuRequest;
import io.github.shimmer.authority.response.SysMenuResponse;
import io.github.shimmer.authority.service.SysMenuService;
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
 * <p>系统菜单路由类</p>
 *
 * @author yu_haiyang
 */

@Rest(path = "menu", name = "菜单管理", description = "top.chsi.system.controller.SysMenuController")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 添加菜单
     *
     * @param request 传递过来的菜单基本信息
     * @return 添加完之后的菜单ID
     */
    @Post(path = "/insert", name = "新增菜单", description = "用于添加菜单，该接口需要登录后并拥有该接口的访问权限")
    @Debounce
    public ApiResult insert(@Validated(VG.C.class) @RequestBody SysMenuRequest request) {
        Long id = sysMenuService.insert(request);
        return ApiResult.ok(id);
    }


    /**
     * 根据菜单的主键获取菜单的信息
     *
     * @param id 菜单的主键信息
     * @return void
     */
    @Delete(path = "delete", name = "删除菜单", description = "根据菜单ID进行对菜单删除，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult delete(@Validated
                                  @NotNull(message = "菜单ID不能为空")
                                  @DecimalMin(value = "1", message = "菜单ID不能小于1")
                                  @Parameter(name = "id", description = "主键")
                                  @RequestParam Long id) {
        sysMenuService.delete(id);
        return ApiResult.ok(id);
    }


    /**
     * 菜单修改
     *
     * @param request 修改后的菜单信息
     * @return void
     */
    @Put(path = "update", name = "修改菜单", description = "根据菜单ID进行对菜单进行修改，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult update(@Validated(VG.U.class) @RequestBody SysMenuRequest request) {
        sysMenuService.update(request);
        return ApiResult.ok();
    }


    /**
     * 根据菜单的主键获取菜单的信息
     *
     * @param id 菜单的主键信息
     * @return 主键对应的菜单详细信息
     */
    @Get(path = "detail", name = "查看菜单详情", description = "根据菜单ID查看菜单的详细信息，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult detail(@NotNull(message = "菜单ID不能为空")
                                             @DecimalMin(value = "1", message = "菜单ID不能小于1")
                                             @Parameter(name = "id", description = "主键")
                                             @RequestParam Long id) {
        SysMenuResponse detail = sysMenuService.detail(id);
        return ApiResult.ok(detail);
    }


    /**
     * 根据条件获取菜单列表
     * 所有的条件注解属性的值为空的时候获取全量
     *
     * @param request 满足指定条件参数的菜单信息
     * @return 满足条件的所有菜单并分页
     */
    @Get(path = "fetch", name = "条件查询菜单列表", description = "根据支持的条件查询字段查看菜单列表信息，该接口需要登录后并拥有该接口的访问权限")
    public Pager fetch(Pager pager, SysMenuRequest request) {
        Pager fetch = sysMenuService.fetch(pager, request);
        return fetch;
    }

    @Get(path = "fetch-tree", name = "查询组织列表以树形结构", description = "查询组织列表以树形结构，该接口需要登录后并拥有该接口的访问权限")
    public List<SysMenuResponse> fetchTree() {
        List<SysMenuResponse> fetch = sysMenuService.fetchTree();
        return fetch;
    }
}
