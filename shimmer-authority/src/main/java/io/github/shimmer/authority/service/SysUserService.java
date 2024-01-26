package io.github.shimmer.authority.service;


import io.github.shimmer.authority.request.SysUserRequest;
import io.github.shimmer.authority.request.UserRoleRequest;
import io.github.shimmer.authority.response.SysUserResponse;
import io.github.shimmer.core.response.data.Pager;

/**
 * <p>系统用户业务接口</p>
 * Created on 2024-01-12 14:20
 *
 * @author yu_haiyang
 */
public interface SysUserService {

    /**
     * 新增
     *
     * @return 插入后的ID
     */
    Long insert(SysUserRequest request);

    /**
     * 根据用户的ID 删除该用户信息
     *
     * @param id 用户的ID
     */
    void delete(Long id);

    /**
     * 更新用户信息
     *
     * @param request 修改后的用户信息
     */
    void update(SysUserRequest request);

    /**
     * 根据用户的ID 查询该用户的详细信息
     *
     * @param id 用户ID
     * @return 该用户的详细信息
     */
    SysUserResponse detail(Long id);

    /**
     * 根据支持用户查询的字段进行对用户进行分页查询
     *
     * @param pager   请求的分页信息
     * @param request 请求的查询信息
     * @return 查询到的用户信息以及分页信息
     */
    Pager<SysUserResponse> fetch(Pager<SysUserResponse> pager, SysUserRequest request);


    void grant(UserRoleRequest request);
}
