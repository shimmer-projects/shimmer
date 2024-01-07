package io.github.shimmer.core.jpa.pager;

import io.github.shimmer.core.data.Pager;
import org.springframework.data.domain.Page;

/**
 * <p>
 * jpa分页对象与系统的分页对象的转换
 * </p>
 * Created on 2024-01-06 15:42
 *
 * @author yu_haiyang
 */
public final class PageMapper {


    public static <T> Pager<T> mapper(Page<T> page) {
        return mapper(new Pager<>(), page);
    }

    public static <T> Pager<T> mapper(Pager<T> pager, Page<T> page) {
        // 当前页
        pager.setPageNo(page.getNumber() + 1);
        // 每页条数
        pager.setPageCount(pager.getPageCount());
        // 总页数
        pager.setPageCount(page.getTotalPages());
        // 总条数
        pager.setRecordCount(page.getTotalElements());
        // 数据内容
        pager.setData(page.getContent());
        // 是否第一页
        pager.setFirst(page.isFirst());
        // 是否有前一页
        pager.setPrevious(page.hasPrevious());
        // 是否有后一页
        pager.setNext(page.hasNext());
        // 是否最后一页
        pager.setLast(page.isLast());
        return pager;
    }
}
