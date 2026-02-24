package com.rykun.chat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ChatEventManager {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String configPath;
    private final Map<ChatEventType, BaseChatEvent> events = new EnumMap<>(ChatEventType.class);

    public ChatEventManager(String configPath) {
        this.configPath = configPath;
        for (ChatEventType type : ChatEventType.values()) {
            events.put(type, new BaseChatEvent(type));
        }
    }

    public void load() {
        Path path = Path.of(configPath);
        try {
            if (!Files.exists(path)) {
                save();
                return;
            }
            Type type = new TypeToken<Map<String, BaseChatEvent>>() {}.getType();
            String content = Files.readString(path);
            Map<String, BaseChatEvent> raw = gson.fromJson(content, type);
            if (raw != null) {
                raw.forEach((k, v) -> events.put(ChatEventType.valueOf(k), v));
            }
        } catch (Exception ignored) {
        }
    }

    public void save() {
        Path path = Path.of(configPath);
        try {
            Files.createDirectories(path.getParent());
            Map<String, BaseChatEvent> output = events.entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey().name(), Map.Entry::getValue));
            Files.writeString(path, gson.toJson(output));
        } catch (IOException ignored) {
        }
    }

    public boolean toggle(String eventName) {
        BaseChatEvent event = getByName(eventName);
        if (event == null) return false;
        event.setEnabled(!event.isEnabled());
        save();
        return true;
    }

    public boolean set(String eventName, String key, String value) {
        BaseChatEvent event = getByName(eventName);
        if (event == null) return false;
        event.setSetting(key, value);
        save();
        return true;
    }

    public String describe(String eventName) {
        BaseChatEvent event = getByName(eventName);
        return event == null
                ? "§cEvento não encontrado."
                : "§e" + event.getType() + " enabled=" + event.isEnabled() + " settings=" + event.getSettings();
    }

    private BaseChatEvent getByName(String eventName) {
        try {
            return events.get(ChatEventType.valueOf(eventName.toUpperCase()));
        } catch (Exception e) {
            return null;
        }
    }
}
