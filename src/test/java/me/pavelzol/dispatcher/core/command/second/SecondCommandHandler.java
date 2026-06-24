package me.pavelzol.dispatcher.core.command.second;

import me.pavelzol.dispatcher.core.api.CommandHandler;

import java.util.List;

public final class SecondCommandHandler implements CommandHandler<SecondCommand> {
    private final List<String> handledCommands;

    public SecondCommandHandler(List<String> handledCommands) {
        this.handledCommands = handledCommands;
    }

    @Override
    public void handle(SecondCommand command) {
        handledCommands.add("second:" + command.value());
    }
}
