package io.easycqrs.core.command.first;

import io.easycqrs.command.CommandHandler;

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
