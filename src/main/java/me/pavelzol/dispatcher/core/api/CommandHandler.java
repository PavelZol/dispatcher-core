package me.pavelzol.dispatcher.core.api;

@FunctionalInterface
public interface CommandHandler<T> {
    void handle(T command);
}
