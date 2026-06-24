package me.pavelzol.dispatcher.core.query.finduserprofile;

import me.pavelzol.dispatcher.core.api.Query;

public record FindUserProfileQuery(long userId) implements Query<FindUserProfileResponse> {
}
