package me.pavelzol.dispatcher.core.dispatcher;

public interface QueryDispatcher {
    <Q, R> R dispatch(Q query);
}
