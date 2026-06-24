package io.easycqrs.core.query.finduserage;

import io.easycqrs.core.api.QueryHandler;

public final class FindUserAgeQueryHandler implements QueryHandler<FindUserAgeQuery, Integer> {

    @Override
    public Integer handle(FindUserAgeQuery query) {
        return Math.toIntExact(query.userId() + 20);
    }
}
