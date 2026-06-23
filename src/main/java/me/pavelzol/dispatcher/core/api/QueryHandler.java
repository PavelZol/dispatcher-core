package me.pavelzol.dispatcher.core.api;

public interface QueryHandler<Q, R> {
    R handle(Q command);
}
