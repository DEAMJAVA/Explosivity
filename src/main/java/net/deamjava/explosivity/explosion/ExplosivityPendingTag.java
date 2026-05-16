package net.deamjava.explosivity.explosion;

public final class ExplosivityPendingTag {

    private static ExplosionSource pending = null;

    public static void set(ExplosionSource source) {
        pending = source;
    }

    public static ExplosionSource takeAndClear() {
        ExplosionSource result = pending;
        pending = null;
        return result;
    }
}