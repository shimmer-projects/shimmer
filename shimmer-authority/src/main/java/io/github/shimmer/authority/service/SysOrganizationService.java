package io.github.shimmer.authority.service;


import io.github.shimmer.authority.request.SysOrganizationRequest;
import io.github.shimmer.authority.response.SysOrganizationResponse;
import io.github.shimmer.core.response.data.Pager;

import java.util.List;

/**
 * <p>组织机构业务接口</p>
 * Created on 2024-01-12 14:21
 *
 * @author yu_haiyang
 */
public interface SysOrganizationService
        // extends BaseTreeService<SysOrganizationEntity>
{


    /**
     * 新增
     *
     * @return 插入后的ID
     */
    Long insert(SysOrganizationRequest request);

    /**
     * 根据组织的ID 删除该组织信息
     *
     * @param id 组织的ID
     */
    void delete(Long id);

    /**
     * 更新组织信息
     *
     * @param request 修改后的组织信息
     */
    void update(SysOrganizationRequest request);

    /**
     * 根据组织的ID 查询该组织的详细信息
     *
     * @param id 组织ID
     * @return 该组织的详细信息
     */
    SysOrganizationResponse detail(Long id);

    /**
     * 根据支持组织查询的字段进行对组织进行分页查询
     *
     * @param pager   请求的分页信息
     * @param request 请求的查询信息
     * @return 查询到的组织信息以及分页信息
     */
    Pager fetch(Pager pager, SysOrganizationRequest request);


    /**
     * 以树形结构返回所有的组织信息
     *
     * @return 树形结构的组织信息
     */
    List<SysOrganizationResponse> fetchTree();
}
