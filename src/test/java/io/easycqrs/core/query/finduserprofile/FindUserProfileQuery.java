package io.easycqrs.core.query.finduserprofile;

import io.easycqrs.core.api.Query;

public record FindUserProfileQuery(long userId) implements Query<FindUserProfileResponse> {
}
