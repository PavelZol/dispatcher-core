package me.pavelzol.dispatcher.core.dispatcher.basic;

import me.pavelzol.dispatcher.core.api.CommandHandler;

import java.util.List;

final class FirstCommandHandler implements CommandHandler<FirstCommand> {
    private final List<String> handledCommands;

    FirstCommandHandler(List<String> handledCommands) {
        this.handledCommands = handledCommands;
    }

    @Override
    public void handle(FirstCommand command) {
        handledCommands.add("first:" + command.value());
    }
}
