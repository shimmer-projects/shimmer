package io.github.shimmer.core.jpa.wrapper;

/**
 * <p>顶级包装器</p>
 * Created on 2024-01-20 19:25
 *
 * @author yu_haiyang
 */
public interface Wrapper {

    default <T> QueryWrapper<T> asWrapper() {
        // TODO 将实体类自身转化为querywrapper
        QueryWrapper<T> wrapper = QueryWrapper.<T>wrapper().build();
//        Field[] fields = this.getClass().getDeclaredFields();
//        try {
//            for (Field field : fields) {
//                field.setAccessible(true);
//                Object tempValue = field.get(this);
//                if (tempValue == null || "".equals(tempValue)) {
//                    continue;
//                }
//                if (field.isAnnotationPresent(Equal.class)) {
//                    wrapper.eq(field.getName(), tempValue);
//                } else if (field.isAnnotationPresent(Like.class)) {
//                    wrapper.like(field.getName(), tempValue.toString());
//                } else if (field.isAnnotationPresent(GreatEqual.class)) {
//                    wrapper.ge(field.getName(), (Comparable<?>) tempValue);
//                } else if (field.isAnnotationPresent(LessEqual.class)) {
//                    wrapper.le(field.getName(), (Comparable<?>) tempValue);
////                } else if (field.isAnnotationPresent(GroupOrLike.class)) {
////                    final String[] tempFields = field.getAnnotation(GroupOrLike.class).fields();
////                    wrapper.or(tempWrapper -> {
////                        for (String tempField : tempFields) {
////                            tempWrapper.like(tempField, tempValue.toString());
////                        }
////                    });
//                }
//            }
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
        return wrapper;
    }
}
