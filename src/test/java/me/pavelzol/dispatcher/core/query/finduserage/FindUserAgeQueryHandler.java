package me.pavelzol.dispatcher.core.query.finduserage;

import me.pavelzol.dispatcher.core.api.QueryHandler;

public final class FindUserAgeQueryHandler implements QueryHandler<FindUserAgeQuery, Integer> {

    @Override
    public Integer handle(FindUserAgeQuery query) {
        return Math.toIntExact(query.userId() + 20);
    }
}
