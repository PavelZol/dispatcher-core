package io.easycqrs.core.dispatcher;

import io.easycqrs.core.api.Query;

public interface QueryDispatcher {
    <R> R dispatch(Query<R> query);
}
