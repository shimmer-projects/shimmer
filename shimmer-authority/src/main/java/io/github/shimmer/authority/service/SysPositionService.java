package io.github.shimmer.authority.service;


import io.github.shimmer.authority.request.SysPositionRequest;
import io.github.shimmer.authority.response.SysPositionResponse;
import io.github.shimmer.core.response.data.Pager;

/**
 * <p>职位业务接口</p>
 * Created on 2024-01-12 14:21
 *
 * @author yu_haiyang
 */
public interface SysPositionService {


    /**
     * 新增
     *
     * @return 插入后的ID
     */
    Long insert(SysPositionRequest request);

    /**
     * 根据职位的ID 删除该职位信息
     *
     * @param id 职位的ID
     */
    void delete(Long id);

    /**
     * 更新职位信息
     *
     * @param request 修改后的职位信息
     */
    void update(SysPositionRequest request);

    /**
     * 根据职位的ID 查询该职位的详细信息
     *
     * @param id 职位ID
     * @return 该职位的详细信息
     */
    SysPositionResponse detail(Long id);

    /**
     * 根据支持职位查询的字段进行对职位进行分页查询
     *
     * @param pager   请求的分页信息
     * @param request 请求的查询信息
     * @return 查询到的职位信息以及分页信息
     */
    Pager<SysPositionResponse> fetch(Pager<SysPositionResponse> pager, SysPositionRequest request);


}
