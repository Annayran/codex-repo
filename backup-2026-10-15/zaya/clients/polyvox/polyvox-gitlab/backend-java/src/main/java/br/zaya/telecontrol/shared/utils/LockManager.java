package br.zaya.telecontrol.shared.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Simple in-memory lock manager with TTL to avoid concurrent processing of the same OS
 * within the same JVM. Non-disruptive helper.
 */
public class LockManager {
    private static final ConcurrentHashMap<String, Long> locks = new ConcurrentHashMap<>();
    // default TTL 5 minutes
    private static final long DEFAULT_TTL_MS = TimeUnit.MINUTES.toMillis(5);

    public static boolean tryAcquire(String key) {
        return tryAcquire(key, DEFAULT_TTL_MS);
    }

    public static boolean tryAcquire(String key, long ttlMs) {
        long now = System.currentTimeMillis();
        Long expiry = locks.get(key);
        if (expiry != null && expiry > now) {
            return false; // someone holds the lock
        }
        long newExpiry = now + ttlMs;
        locks.put(key, newExpiry);
        return true;
    }

    public static void release(String key) {
        locks.remove(key);
    }
}


