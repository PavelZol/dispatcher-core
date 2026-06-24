package me.pavelzol.dispatcher.core.query.findusersettings;

import me.pavelzol.dispatcher.core.api.QueryHandler;

public final class FindUserSettingsQueryHandler implements QueryHandler<FindUserSettingsQuery, FindUserSettingsResponse> {

    @Override
    public FindUserSettingsResponse handle(FindUserSettingsQuery query) {
        FindUserSettingsResponse settings = new FindUserSettingsResponse();
        settings.setUserId(query.userId());
        settings.setNotificationsEnabled(true);
        settings.setTheme("dark");
        return settings;
    }
}
