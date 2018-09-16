package com.simplexorg.customviews.util;

public class VFactory {
    private static VFactory mFactory;

    public <T> T create(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static VFactory getInstance() {
        if (mFactory == null) {
            mFactory = new VFactory();
        }
        return mFactory;
    }
}
