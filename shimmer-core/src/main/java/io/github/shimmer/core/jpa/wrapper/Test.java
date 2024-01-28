package io.github.shimmer.core.jpa.wrapper;

import io.github.shimmer.core.jpa.entity.BaseEntity;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p></p>
 * Created on 2024-01-21 19:20
 *
 * @author yu_haiyang
 */
public class Test {

    public static void main(String[] args) {
//        Specification<BaseEntity<Long>> eq1 = new LambdaQueryWrapper<BaseEntity<Long>>(null, null)
//                .eq(BaseEntity::getId, 5);
//        System.out.println(eq1);
//        Wrapper<String, FieldFunction<String, String>> eq = new TestWrapper<String, String, String>().eq();
        new QueryWrapper.Wrapper<BaseEntity<Long>>().eq("name", "a").build();
    }
}
