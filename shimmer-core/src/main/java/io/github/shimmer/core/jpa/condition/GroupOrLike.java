package io.github.shimmer.core.jpa.condition;

import java.lang.annotation.*;

/**
 * 比如姓名或者昵称都包含 张的就可以给fields 传name和nickName
 *
 * @author yu_haiyang
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GroupOrLike {
    String[] fields();
}
