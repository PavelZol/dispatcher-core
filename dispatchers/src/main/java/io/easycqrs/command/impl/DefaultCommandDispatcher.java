package io.easycqrs.command.impl;

import io.easycqrs.command.CommandDispatcher;
import io.easycqrs.command.CommandHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class DefaultCommandDispatcher implements CommandDispatcher {
    private final Map<Class<?>, CommandHandler<?>> commandHandlersMap;

    private DefaultCommandDispatcher(Map<Class<?>, CommandHandler<?>> commandHandlersMap) {
        this.commandHandlersMap = Map.copyOf(commandHandlersMap);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public <T> void dispatch(T command) {
        Objects.requireNonNull(command, "command must not be null");

        Class<?> commandType = command.getClass();
        CommandHandler<?> commandHandler = commandHandlersMap.get(commandType);
        if (commandHandler == null) {
            throw new IllegalArgumentException(
                "No command handler registered for command type: " + commandType.getName()
            );
        }

        handle(commandHandler, command);
    }

    @SuppressWarnings("unchecked")
    private static <T> void handle(CommandHandler<?> commandHandler, T command) {
        ((CommandHandler<T>) commandHandler).handle(command);
    }

    public static final class Builder {
        private final Map<Class<?>, CommandHandler<?>> commandHandlersMap = new HashMap<>();

        private Builder() {
        }

        public <T> Builder register(Class<T> commandType, CommandHandler<? super T> commandHandler) {
            Objects.requireNonNull(commandType, "commandType must not be null");
            Objects.requireNonNull(commandHandler, "commandHandler must not be null");

            CommandHandler<?> previousCommandHandler = commandHandlersMap.putIfAbsent(commandType, commandHandler);
            if (previousCommandHandler != null) {
                throw new IllegalArgumentException(
                    "Duplicate command handler for command type: " + commandType.getName()
                );
            }

            return this;
        }

        public CommandDispatcher build() {
            return new DefaultCommandDispatcher(commandHandlersMap);
        }
    }
}
