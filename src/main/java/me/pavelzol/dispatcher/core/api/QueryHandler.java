package me.pavelzol.dispatcher.core.api;

@FunctionalInterface
public interface QueryHandler<Q, R> {
    R handle(Q query);
}
