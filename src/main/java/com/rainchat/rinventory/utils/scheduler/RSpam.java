package com.rainchat.rinventory.utils.scheduler;

import com.rainchat.rinventory.RCore;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public final class RSpam {

    private static final Set<String> spams = new HashSet<>();

    /**
     * Checks if id is spamming.
     *
     * @param id       The id to check.
     * @param duration The duration to check.
     * @return True if spamming.
     */
    public static boolean spam( String id,  Duration duration) {
        return RSpam.spam(id, duration.toMillis());
    }

    /**
     * Checks if id is spamming.
     *
     * @param id   The id to check.
     * @param time The time in milliseconds.
     * @return True if spamming.
     */
    public static boolean spam( String id, long time) {
        if (!RSpam.spams.contains(id)) {
            RSpam.spams.add(id);
            RCore.syncScheduler().after(time / 50)
                    .run(() -> RSpam.spams.remove(id));
            return false;
        }
        return true;
    }
}