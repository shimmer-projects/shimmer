package io.github.shimmer.core.validator;

import jakarta.validation.groups.Default;

/**
 * <p>ValidationGroup：spring validator验证组的定义</p>
 * Created on 2023-12-19 16:24
 *
 * @author yu_haiyang
 */
public interface VG {

    /**
     * 增删改查的集中验证组的定义
     * <p>
     * 1、C: Create(创建)，当然数据库的表插入是insert，创建是Create
     * <p>
     * 2、R: Retrieve(查询)，就是select
     * <p>
     * 3、U: Update(更新)，就是Update
     * <p>
     * 4、D: Delete(删除)，就是Delete
     * <p>
     * 增 Create
     */
    interface C extends Default {
    }

    /**
     * 查 Retrieve
     */
    interface R extends Default {
    }

    /**
     * 改 Update
     */
    interface U extends Default {
    }

    /**
     * 删 Delete
     */
    interface D extends Default {
    }
}
