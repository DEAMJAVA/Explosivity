package net.deamjava.explosivity.explosion;

public final class ExplosivityPendingTag {

    // ServerExplosion is always constructed synchronously on the server thread
    // inside Level.explode(), so a single static field is safe here.
    // Block mixin sets this BEFORE calling level.explode(); constructor mixin
    // reads and clears it, taking priority over the auto-resolved value.
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