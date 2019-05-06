package com.ilkerkorkut.utils;

import java.util.Random;
import java.util.UUID;

public class AppUtils {

    private static final int MAX_ELECTION_TIMEOUT = 300;
    private static final int MIN_ELECTION_TIMEOUT = 150;

    private static final int HEARTBEAT_TIMEOUT = 75;

    private AppUtils() {
        throw new IllegalStateException("Static utility class.");
    }

    public static int getRandomElectionTimeout() {
        Random r = new Random();
        return r.nextInt((MAX_ELECTION_TIMEOUT - MIN_ELECTION_TIMEOUT) + 1) + MIN_ELECTION_TIMEOUT;
    }

    public static int getHeartBeatTimeout() {
        return HEARTBEAT_TIMEOUT;
    }

    public static String getRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
