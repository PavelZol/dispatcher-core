package me.pavelzol.dispatcher.core.dispatcher;

public interface CommandDispatcher {
    <T> void dispatch(T command);
}
