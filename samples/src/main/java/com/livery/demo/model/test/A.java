package com.livery.demo.model.test;

public class A<T> {
    public T natureT;

    public static A getInstance() {
        return new A();
    }

    public static <T> A<T> genericInstance() {
        return new A<>();
    }

    public static <T> A<T> response() {
        return new A<T>();
    }

    static <T> A<T> response(final Class<T> service) {
        return null;
    }

    T getTTT(final Class<T> service) {
        //task转换T
        return natureT;
    }

    T getTTT() {
        //task转换T
        return natureT;
    }

    T genericInstance(final Class<T> service) {
        natureT = (T) service.getClass();
        return natureT;
    }

    T getGenericClass() {
        if (natureT == null) {
            return null;
        } else {
            return natureT;
        }
    }

    <T> T create(final Class<T> service) {
        return null;
    }
}
