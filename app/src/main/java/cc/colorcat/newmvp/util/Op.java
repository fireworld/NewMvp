package cc.colorcat.newmvp.util;

/**
 * Created by cxx on 16-8-11.
 * xx.ch@outlook.com
 */
public final class Op {
    public static <T> T nullElse(T value, T other) {
        return value != null ? value : other;
    }

    public static <T> T nonNull(T value) {
        if (value == null) {
            throw new NullPointerException("value == null");
        }
        return value;
    }

    public static <T> T nonNull(T value, String msg) {
        if (value == null) {
            throw new NullPointerException(msg);
        }
        return value;
    }

    public static <T, E extends Throwable> T nullThrow(T value, E e) throws E {
        if (value == null) {
            throw e;
        }
        return value;
    }

    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }

    public static <T extends CharSequence> T emptyElse(T value, T other) {
        return isEmpty(value) ? other : value;
    }

    private Op() {
        throw new AssertionError("no instance");
    }
}
