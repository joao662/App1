package com.rykun;

import com.rykun.bridge.EssentialsBridge;
import com.rykun.chat.ChatEventManager;
import com.rykun.commands.RykunEventosCommand;
import com.rykun.core.ArenaEventManager;

/**
 * Classe principal do plugin. Mantida desacoplada da API para facilitar testes.
 */
public class RykunEventos {
    private final ChatEventManager chatEventManager;
    private final ArenaEventManager arenaEventManager;
    private final EssentialsBridge essentialsBridge;
    private final RykunEventosCommand command;

    public RykunEventos(String dataFolderPath) {
        this.chatEventManager = new ChatEventManager(dataFolderPath + "/config/chat-events.json");
        this.arenaEventManager = new ArenaEventManager(dataFolderPath + "/config/arena-events.json");
        this.essentialsBridge = new EssentialsBridge();
        this.command = new RykunEventosCommand(chatEventManager, arenaEventManager);
    }

    public void onEnable() {
        chatEventManager.load();
        arenaEventManager.load();
    }

    public void onDisable() {
        chatEventManager.save();
        arenaEventManager.save();
    }

    public String handleCommand(String sender, String[] args) {
        return command.handle(sender, args);
    }

    public EssentialsBridge getEssentialsBridge() {
        return essentialsBridge;
    }
}
