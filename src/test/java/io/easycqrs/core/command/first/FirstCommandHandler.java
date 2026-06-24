package io.easycqrs.core.command.first;

import io.easycqrs.core.api.CommandHandler;

import java.util.List;

public final class FirstCommandHandler implements CommandHandler<FirstCommand> {
    private final List<String> handledCommands;

    public FirstCommandHandler(List<String> handledCommands) {
        this.handledCommands = handledCommands;
    }

    @Override
    public void handle(FirstCommand command) {
        handledCommands.add("first:" + command.value());
    }
}
