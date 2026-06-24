package io.easycqrs.core.query.findusername;

import io.easycqrs.core.api.QueryHandler;

public final class FindUserNameQueryHandler implements QueryHandler<FindUserNameQuery, String> {

    @Override
    public String handle(FindUserNameQuery query) {
        return "user-" + query.userId();
    }
}
