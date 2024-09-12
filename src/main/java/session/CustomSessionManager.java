package session;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CustomSessionManager {
    private static final ConcurrentHashMap<String, Object> sessionAttributes = new ConcurrentHashMap<>();
    private static String sessionId;

    static {
        // Generate a unique session ID when the class is loaded
        sessionId = UUID.randomUUID().toString();
    }

    public static void setAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }

    public static Object getAttribute(String key) {
        return sessionAttributes.get(key);
    }

    public static void removeAttribute(String key) {
        sessionAttributes.remove(key);
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void regenerateSessionId() {
        sessionId = UUID.randomUUID().toString();
    }
}