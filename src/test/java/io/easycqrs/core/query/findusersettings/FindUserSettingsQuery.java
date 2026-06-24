package io.easycqrs.core.query.findusersettings;

import io.easycqrs.query.Query;

public record FindUserSettingsQuery(long userId) implements Query<FindUserSettingsResponse> {
}
