package io.easycqrs.core.query.findusersettings;

import io.easycqrs.core.api.Query;

public record FindUserSettingsQuery(long userId) implements Query<FindUserSettingsResponse> {
}
