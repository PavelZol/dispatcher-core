package io.easycqrs.command;

public interface CommandDispatcher {
    <T> void dispatch(T command);
}
