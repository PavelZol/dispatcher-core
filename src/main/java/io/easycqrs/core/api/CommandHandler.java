package io.easycqrs.core.api;

@FunctionalInterface
public interface CommandHandler<T> {
    void handle(T command);
}
