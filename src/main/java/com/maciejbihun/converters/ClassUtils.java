package com.maciejbihun.converters;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ClassUtils {

    protected ClassUtils() { }

    public static  void setIfNotNull(final Supplier getter, final Consumer setter) {
        Object t = getter.get();
        if (null != t) {
            setter.accept(t);
        }
    }

}
