package me.pavelzol.dispatcher.core.dispatcher;

import me.pavelzol.dispatcher.core.api.Query;
import me.pavelzol.dispatcher.core.api.QueryHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class BasicQueryDispatcherImpl implements QueryDispatcher {
    private final Map<Class<?>, QueryHandler<?, ?>> queryHandlersMap;

    private BasicQueryDispatcherImpl(Map<Class<?>, QueryHandler<?, ?>> queryHandlersMap) {
        this.queryHandlersMap = Map.copyOf(queryHandlersMap);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public <R> R dispatch(Query<R> query) {
        Objects.requireNonNull(query, "query must not be null");

        Class<?> queryType = query.getClass();
        QueryHandler<?, ?> queryHandler = queryHandlersMap.get(queryType);
        if (queryHandler == null) {
            throw new IllegalArgumentException(
                "No query handler registered for query type: " + queryType.getName()
            );
        }

        return handle(queryHandler, query);
    }

    @SuppressWarnings("unchecked")
    private static <Q extends Query<R>, R> R handle(QueryHandler<?, ?> queryHandler, Query<R> query) {
        return ((QueryHandler<Q, R>) queryHandler).handle((Q) query);
    }

    public static final class Builder {
        private final Map<Class<?>, QueryHandler<?, ?>> queryHandlersMap = new HashMap<>();

        private Builder() {
        }

        public <Q extends Query<R>, R> Builder register(
            Class<Q> queryType,
            QueryHandler<? super Q, ? extends R> queryHandler
        ) {
            Objects.requireNonNull(queryType, "queryType must not be null");
            Objects.requireNonNull(queryHandler, "queryHandler must not be null");

            QueryHandler<?, ?> previousQueryHandler = queryHandlersMap.putIfAbsent(queryType, queryHandler);
            if (previousQueryHandler != null) {
                throw new IllegalArgumentException(
                    "Duplicate query handler for query type: " + queryType.getName()
                );
            }

            return this;
        }

        public QueryDispatcher build() {
            return new BasicQueryDispatcherImpl(queryHandlersMap);
        }
    }
}
