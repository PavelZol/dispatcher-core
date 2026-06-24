package me.pavelzol.dispatcher.core.dispatcher;

import me.pavelzol.dispatcher.core.api.Query;

public interface QueryDispatcher {
    <R> R dispatch(Query<R> query);
}
