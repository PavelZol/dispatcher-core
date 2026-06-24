package me.pavelzol.dispatcher.core.dispatcher.basic;

import me.pavelzol.dispatcher.core.api.CommandHandler;

import java.util.List;

final class SecondCommandHandler implements CommandHandler<SecondCommand> {
    private final List<String> handledCommands;

    SecondCommandHandler(List<String> handledCommands) {
        this.handledCommands = handledCommands;
    }

    @Override
    public void handle(SecondCommand command) {
        handledCommands.add("second:" + command.value());
    }
}
