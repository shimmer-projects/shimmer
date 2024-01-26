package io.github.shimmer.authority.controller;

import io.github.shimmer.authority.request.SysUserRequest;
import io.github.shimmer.authority.request.UserRoleRequest;
import io.github.shimmer.authority.response.SysUserResponse;
import io.github.shimmer.authority.service.SysUserService;
import io.github.shimmer.core.annotation.Get;
import io.github.shimmer.core.annotation.Put;
import io.github.shimmer.core.annotation.Rest;
import io.github.shimmer.core.debounce.Debounce;
import io.github.shimmer.core.response.data.ApiResult;
import io.github.shimmer.core.response.data.Pager;
import io.github.shimmer.core.validator.multipart.MultipartFileVerify;
import io.github.shimmer.core.validator.multipart.constant.FileType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * <p>用户管理</p>
 *
 * @author yu_haiyang
 */
@Rest(path = "user", name = "用户管理", description = "用户管理相关的api接口")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    /**
     * 添加用户
     *
     * @param request 传递过来的用户基本信息
     * @return 添加完之后的用户ID
     */
    @PostMapping(path = "/insert")
    @Operation(summary = "新增用户", description = "用于添加用户，该接口需要登录后并拥有该接口的访问权限")
    @Debounce
    public ApiResult<Long> insert(@Validated @RequestBody SysUserRequest request) {
        Long id = sysUserService.insert(request);
        return ApiResult.ok(id);
    }

    /**
     * 根据用户的主键获取用户的信息
     *
     * @param id 用户的主键信息
     * @return void
     */
    @DeleteMapping(path = "delete")
    @Operation(summary = "删除用户", description = "根据用户ID进行对用户删除，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult<Long> delete(@Validated
                                  @NotNull(message = "用户ID不能为空")
                                  @DecimalMin(value = "1", message = "用户ID不能小于1")
                                  @Parameter(name = "id", description = "主键")
                                  @RequestParam Long id) {
        sysUserService.delete(id);
        return ApiResult.ok(id);
    }


    /**
     * 用户修改
     *
     * @param request 修改后的用户信息
     * @return void
     */
    @PutMapping(path = "update")
    @Operation(summary = "修改用户", description = "根据用户ID进行对用户进行修改，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult<Long> update(@Validated @RequestBody SysUserRequest request) {
        sysUserService.update(request);
        return ApiResult.ok(request.id());
    }

    /**
     * 根据用户的主键获取用户的信息
     *
     * @param id 用户的主键信息
     * @return 主键对应的用户详细信息
     */
    @GetMapping(path = "detail")
    @Operation(summary = "查看用户详情", description = "根据用户ID查看用户的详细信息，该接口需要登录后并拥有该接口的访问权限")
    public ApiResult<SysUserResponse> detail(@NotNull(message = "用户ID不能为空")
                                             @DecimalMin(value = "1", message = "用户ID不能小于1")
                                             @Parameter(name = "id", description = "主键")
                                             @RequestParam Long id) {
        SysUserResponse view = sysUserService.detail(id);
        return ApiResult.ok(view);
    }

    /**
     * 根据条件获取用户列表
     * 所有的条件注解属性的值为空的时候获取全量
     *
     * @param request 满足指定条件参数的用户信息
     * @return 满足条件的所有用户并分页
     */
    @Get(path = "fetch", name = "条件查询用户列表", description = "根据支持的条件查询字段查看用户列表信息，该接口需要登录后并拥有该接口的访问权限")
    public Pager<SysUserResponse> fetch(Pager<SysUserResponse> pager, SysUserRequest request) {
        Pager<SysUserResponse> fetch = sysUserService.fetch(pager, request);
        return fetch;
    }

    @Put(path = "grant", name = "为用户授予角色", description = "roleId 可以以数组的形式同时为一个用户授予多个角色")
    public void grant(@RequestBody UserRoleRequest request) {
        sysUserService.grant(request);
    }

    @PostMapping(path = "upload")
    @Operation(summary = "文件上传", description = "根据用户ID进行对用户进行修改，该接口需要登录后并拥有该接口的访问权限")
    public void upload(@MultipartFileVerify(value = FileType.XLS, notAllow = FileType.EXE, maxSize = 10) MultipartFile file) {
        System.out.println(file);
    }
}
