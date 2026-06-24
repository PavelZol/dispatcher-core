package io.easycqrs.core.query.findusername;

import io.easycqrs.core.api.QueryHandler;

public final class DuplicateFindUserNameQueryHandler implements QueryHandler<FindUserNameQuery, String> {

    @Override
    public String handle(FindUserNameQuery query) {
        return "duplicate-user-" + query.userId();
    }
}
