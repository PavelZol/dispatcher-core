package io.easycqrs.core.dispatcher;

public interface CommandDispatcher {
    <T> void dispatch(T command);
}
