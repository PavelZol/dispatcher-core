package io.easycqrs.query;

public interface QueryDispatcher {
    <R> R dispatch(Query<R> query);
}
