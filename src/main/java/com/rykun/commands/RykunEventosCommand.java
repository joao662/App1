package com.rykun.commands;

import com.rykun.chat.ChatEventManager;
import com.rykun.core.ArenaEventManager;

public class RykunEventosCommand {
    private final ChatEventManager chatEventManager;
    private final ArenaEventManager arenaEventManager;

    public RykunEventosCommand(ChatEventManager chatEventManager, ArenaEventManager arenaEventManager) {
        this.chatEventManager = chatEventManager;
        this.arenaEventManager = arenaEventManager;
    }

    public String handle(String sender, String[] args) {
        if (args.length == 0) {
            return help();
        }

        return switch (args[0].toLowerCase()) {
            case "reload" -> {
                chatEventManager.load();
                arenaEventManager.load();
                yield "§aConfigs recarregadas.";
            }
            case "save" -> {
                chatEventManager.save();
                arenaEventManager.save();
                yield "§aConfigs salvas.";
            }
            case "config" -> handleConfig(args);
            default -> help();
        };
    }

    private String handleConfig(String[] args) {
        if (args.length < 2) {
            return "§cUse: /re config <list|set|toggle> ...";
        }

        return switch (args[1].toLowerCase()) {
            case "list" -> {
                if (args.length < 4) {
                    yield "§cUse: /re config list <chat|arena> <evento>";
                }
                String scope = args[2];
                String eventName = args[3];
                yield scope.equalsIgnoreCase("chat")
                        ? chatEventManager.describe(eventName)
                        : arenaEventManager.describe(eventName);
            }
            case "toggle" -> {
                if (args.length < 4) {
                    yield "§cUse: /re config toggle <chat|arena> <evento>";
                }
                String scope = args[2];
                String eventName = args[3];
                boolean result = scope.equalsIgnoreCase("chat")
                        ? chatEventManager.toggle(eventName)
                        : arenaEventManager.toggle(eventName);
                yield result ? "§aEvento atualizado." : "§cEvento não encontrado.";
            }
            case "set" -> {
                if (args.length < 6) {
                    yield "§cUse: /re config set <chat|arena> <evento> <chave> <valor>";
                }
                String scope = args[2];
                String eventName = args[3];
                String key = args[4];
                String value = args[5];
                boolean ok = scope.equalsIgnoreCase("chat")
                        ? chatEventManager.set(eventName, key, value)
                        : arenaEventManager.set(eventName, key, value);
                yield ok ? "§aConfig alterada." : "§cEvento não encontrado.";
            }
            default -> "§cSubcomando inválido.";
        };
    }

    private String help() {
        return "§e/re reload | /re save | /re config list <chat|arena> <evento> | /re config set <chat|arena> <evento> <chave> <valor> | /re config toggle <chat|arena> <evento>";
    }
}
