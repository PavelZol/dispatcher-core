package io.easycqrs.command;

@FunctionalInterface
public interface CommandHandler<T> {
    void handle(T command);
}
