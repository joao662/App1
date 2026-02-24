package com.rykun.core;

import java.util.HashMap;
import java.util.Map;

public class BaseArenaEvent {
    private final ArenaEventType type;
    private boolean enabled;
    private final Map<String, String> settings;

    public BaseArenaEvent(ArenaEventType type) {
        this.type = type;
        this.enabled = true;
        this.settings = new HashMap<>();
        this.settings.put("minPlayers", "2");
        this.settings.put("maxPlayers", "20");
    }

    public ArenaEventType getType() {
        return type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setSetting(String key, String value) {
        settings.put(key, value);
    }

    public Map<String, String> getSettings() {
        return settings;
    }
}
