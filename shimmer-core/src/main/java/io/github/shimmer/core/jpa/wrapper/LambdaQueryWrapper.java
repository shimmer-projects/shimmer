package io.github.shimmer.core.jpa.wrapper;

import lombok.Builder;

import java.util.List;

/**
 * <p></p>
 * Created on 2024-01-22 20:54
 *
 * @author yu_haiyang
 */
@Builder(builderMethodName = "lambda", builderClassName = "Lambda")
final class LambdaQueryWrapper<T> extends AbstractWrapper<T> {
    protected LambdaQueryWrapper(List<QueryField> queryFields, List<OrderBy> orderBy) {
        super(queryFields, orderBy);
    }

    @Override
    public Wrapper<T> eq(String tFieldFunction, Object v) {
        return null;
    }
}
