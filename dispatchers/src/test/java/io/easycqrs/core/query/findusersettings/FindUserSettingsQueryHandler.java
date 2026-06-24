package io.easycqrs.core.query.findusersettings;

import io.easycqrs.query.QueryHandler;

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
