package io.easycqrs.core.query.finduserprofile;

import io.easycqrs.query.Query;

public record FindUserProfileQuery(long userId) implements Query<FindUserProfileResponse> {
}
