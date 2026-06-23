package me.pavelzol.dispatcher.core.api;

public interface CommandHandler<T> {
    void handle(T command);
}
