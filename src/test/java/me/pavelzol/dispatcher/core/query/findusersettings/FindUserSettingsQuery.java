package me.pavelzol.dispatcher.core.query.findusersettings;

import me.pavelzol.dispatcher.core.api.Query;

public record FindUserSettingsQuery(long userId) implements Query<FindUserSettingsResponse> {
}
