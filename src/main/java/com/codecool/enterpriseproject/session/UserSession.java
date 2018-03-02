package com.codecool.enterpriseproject.session;

import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession{

    private Map<String, String> session = new HashMap<>();

    public UserSession() {
    }

    public void setAttribute(String key, String value) {
        this.session.put(key, value);
    }

    public String getAttribute(String key) {
        return this.session.get(key);
    }

    public void clear() {
        this.session.clear();
    }
}
