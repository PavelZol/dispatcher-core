package me.pavelzol.dispatcher.core.query.finduserprofile;

import me.pavelzol.dispatcher.core.api.QueryHandler;

public final class FindUserProfileQueryHandler implements QueryHandler<FindUserProfileQuery, FindUserProfileResponse> {

    @Override
    public FindUserProfileResponse handle(FindUserProfileQuery query) {
        return new FindUserProfileResponse(
            query.userId(),
            "user-" + query.userId(),
            new FindUserProfileAddressResponse("Amsterdam", "Keizersgracht")
        );
    }
}
