package com.github.fluent.hibernate.transformer;

import org.hibernate.transform.BasicTransformerAdapter;

import com.github.fluent.hibernate.internal.util.InternalUtils.ClassUtils;
import com.github.fluent.hibernate.internal.util.reflection.NestedSetter;

/**
 * @author DoubleF1re
 * @author V.Ladynev
 */
public class FluentHibernateResultTransformer extends BasicTransformerAdapter {

    private static final long serialVersionUID = 6825154815776629666L;

    private final Class<?> resultClass;

    private NestedSetter[] setters;

    public FluentHibernateResultTransformer(Class<?> resultClass) {
        this.resultClass = resultClass;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        createCachedSetters(resultClass, aliases);

        Object result = ClassUtils.newInstance(resultClass);

        for (int i = 0; i < aliases.length; i++) {
            if(setters[i] != null) {
                setters[i].set(result, tuple[i]);
            } else {
                //如果该属性对应的setter方法为null，则忽略该属性
                System.out.println("setter " + aliases[i] + " is null!!!");
            }

        }

        return result;
    }

    private void createCachedSetters(Class<?> resultClass, String[] aliases) {
        if (setters == null) {
            setters = createSetters(resultClass, aliases);
        }
    }

    /**
     *
     * @param resultClass
     * @param aliases 数据库查询的字段
     * @return
     */
    private static NestedSetter[] createSetters(Class<?> resultClass, String[] aliases) {
        NestedSetter[] result = new NestedSetter[aliases.length];

        for (int i = 0; i < aliases.length; i++) {
            NestedSetter tmpSetter = NestedSetter.create(resultClass, aliases[i]);
            //如果数据库查询的字段在POJO中没有直接跳过
            if(tmpSetter != null) {

                result[i] = tmpSetter;
            } else {
                System.out.println("Can't find property" + aliases[i]);
            }

        }

        return result;
    }

}
