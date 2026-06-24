package me.pavelzol.dispatcher.core.query.findusername;

import me.pavelzol.dispatcher.core.api.QueryHandler;

public final class FindUserNameQueryHandler implements QueryHandler<FindUserNameQuery, String> {

    @Override
    public String handle(FindUserNameQuery query) {
        return "user-" + query.userId();
    }
}
