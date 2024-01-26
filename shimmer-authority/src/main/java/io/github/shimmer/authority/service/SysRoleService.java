package io.github.shimmer.authority.service;


import io.github.shimmer.authority.request.SysRoleRequest;
import io.github.shimmer.authority.response.SysRoleResponse;
import io.github.shimmer.core.response.data.Pager;

/**
 * <p>角色业务接口</p>
 * Created on 2024-01-12 14:21
 *
 * @author yu_haiyang
 */
public interface SysRoleService {


    /**
     * 新增
     *
     * @return 插入后的ID
     */
    Long insert(SysRoleRequest request);

    /**
     * 根据角色的ID 删除该角色信息
     *
     * @param id 角色的ID
     */
    void delete(Long id);

    /**
     * 更新角色信息
     *
     * @param request 修改后的角色信息
     */
    void update(SysRoleRequest request);

    /**
     * 根据角色的ID 查询该角色的详细信息
     *
     * @param id 角色ID
     * @return 该角色的详细信息
     */
    SysRoleResponse detail(Long id);

    /**
     * 根据支持角色查询的字段进行对角色进行分页查询
     *
     * @param pager   请求的分页信息
     * @param request 请求的查询信息
     * @return 查询到的角色信息以及分页信息
     */
    Pager<SysRoleResponse> fetch(Pager<SysRoleResponse> pager, SysRoleRequest request);

}
