package com.rykun.chat;

import java.util.HashMap;
import java.util.Map;

public class BaseChatEvent {
    private final ChatEventType type;
    private boolean enabled;
    private final Map<String, String> settings;

    public BaseChatEvent(ChatEventType type) {
        this.type = type;
        this.enabled = true;
        this.settings = new HashMap<>();
        this.settings.put("reward", "100");
        this.settings.put("cooldown", "300");
    }

    public ChatEventType getType() {
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
