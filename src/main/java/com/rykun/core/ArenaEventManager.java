package com.rykun.core;

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

public class ArenaEventManager {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String configPath;
    private final Map<ArenaEventType, BaseArenaEvent> events = new EnumMap<>(ArenaEventType.class);

    public ArenaEventManager(String configPath) {
        this.configPath = configPath;
        for (ArenaEventType type : ArenaEventType.values()) {
            events.put(type, new BaseArenaEvent(type));
        }
    }

    public void load() {
        Path path = Path.of(configPath);
        try {
            if (!Files.exists(path)) {
                save();
                return;
            }
            Type type = new TypeToken<Map<String, BaseArenaEvent>>() {}.getType();
            String content = Files.readString(path);
            Map<String, BaseArenaEvent> raw = gson.fromJson(content, type);
            if (raw != null) {
                raw.forEach((k, v) -> events.put(ArenaEventType.valueOf(k), v));
            }
        } catch (Exception ignored) {
        }
    }

    public void save() {
        Path path = Path.of(configPath);
        try {
            Files.createDirectories(path.getParent());
            Map<String, BaseArenaEvent> output = events.entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey().name(), Map.Entry::getValue));
            Files.writeString(path, gson.toJson(output));
        } catch (IOException ignored) {
        }
    }

    public boolean toggle(String eventName) {
        BaseArenaEvent event = getByName(eventName);
        if (event == null) return false;
        event.setEnabled(!event.isEnabled());
        save();
        return true;
    }

    public boolean set(String eventName, String key, String value) {
        BaseArenaEvent event = getByName(eventName);
        if (event == null) return false;
        event.setSetting(key, value);
        save();
        return true;
    }

    public String describe(String eventName) {
        BaseArenaEvent event = getByName(eventName);
        return event == null
                ? "§cEvento não encontrado."
                : "§e" + event.getType() + " enabled=" + event.isEnabled() + " settings=" + event.getSettings();
    }

    private BaseArenaEvent getByName(String eventName) {
        try {
            return events.get(ArenaEventType.valueOf(eventName.toUpperCase()));
        } catch (Exception e) {
            return null;
        }
    }
}
