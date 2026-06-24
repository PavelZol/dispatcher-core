package io.easycqrs.core.query.findusername;

import io.easycqrs.query.QueryHandler;

public final class DuplicateFindUserNameQueryHandler implements QueryHandler<FindUserNameQuery, String> {

    @Override
    public String handle(FindUserNameQuery query) {
        return "duplicate-user-" + query.userId();
    }
}
