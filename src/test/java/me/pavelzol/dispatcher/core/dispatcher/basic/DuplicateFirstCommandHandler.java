package me.pavelzol.dispatcher.core.dispatcher.basic;

import me.pavelzol.dispatcher.core.api.CommandHandler;

import java.util.List;

final class DuplicateFirstCommandHandler implements CommandHandler<FirstCommand> {
    private final List<String> handledCommands;

    DuplicateFirstCommandHandler(List<String> handledCommands) {
        this.handledCommands = handledCommands;
    }

    @Override
    public void handle(FirstCommand command) {
        handledCommands.add("duplicate-first:" + command.value());
    }
}
