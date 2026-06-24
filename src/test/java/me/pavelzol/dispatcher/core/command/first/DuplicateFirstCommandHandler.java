package me.pavelzol.dispatcher.core.command.first;

import me.pavelzol.dispatcher.core.api.CommandHandler;

import java.util.List;

public final class DuplicateFirstCommandHandler implements CommandHandler<FirstCommand> {
    private final List<String> handledCommands;

    public DuplicateFirstCommandHandler(List<String> handledCommands) {
        this.handledCommands = handledCommands;
    }

    @Override
    public void handle(FirstCommand command) {
        handledCommands.add("duplicate-first:" + command.value());
    }
}
