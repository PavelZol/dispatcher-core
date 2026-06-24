package io.easycqrs.core.query;

import io.easycqrs.query.impl.DefaultQueryDispatcher;
import io.easycqrs.query.QueryDispatcher;
import io.easycqrs.core.query.finduserage.FindUserAgeQuery;
import io.easycqrs.core.query.finduserage.FindUserAgeQueryHandler;
import io.easycqrs.core.query.findusername.DuplicateFindUserNameQueryHandler;
import io.easycqrs.core.query.findusername.FindUserNameQuery;
import io.easycqrs.core.query.findusername.FindUserNameQueryHandler;
import io.easycqrs.core.query.finduserprofile.FindUserProfileAddressResponse;
import io.easycqrs.core.query.finduserprofile.FindUserProfileQuery;
import io.easycqrs.core.query.finduserprofile.FindUserProfileQueryHandler;
import io.easycqrs.core.query.finduserprofile.FindUserProfileResponse;
import io.easycqrs.core.query.findusersettings.FindUserSettingsQuery;
import io.easycqrs.core.query.findusersettings.FindUserSettingsQueryHandler;
import io.easycqrs.core.query.findusersettings.FindUserSettingsResponse;
import io.easycqrs.core.query.unknownquery.UnknownQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultQueryDispatcherTest {

    @Test
    void dispatchesQueryToRegisteredHandlerAndReturnsResponse() {
        QueryDispatcher queryDispatcher = DefaultQueryDispatcher.builder()
            .register(FindUserNameQuery.class, new FindUserNameQueryHandler())
            .register(FindUserAgeQuery.class, new FindUserAgeQueryHandler())
            .register(FindUserProfileQuery.class, new FindUserProfileQueryHandler())
            .register(FindUserSettingsQuery.class, new FindUserSettingsQueryHandler())
            .build();

        String userName = queryDispatcher.dispatch(new FindUserNameQuery(42));
        Integer userAge = queryDispatcher.dispatch(new FindUserAgeQuery(7));
        FindUserProfileResponse userProfile = queryDispatcher.dispatch(new FindUserProfileQuery(42));
        FindUserSettingsResponse userSettings = queryDispatcher.dispatch(new FindUserSettingsQuery(42));

        assertEquals("user-42", userName);
        assertEquals(27, userAge);
        assertEquals(
            new FindUserProfileResponse(
                42,
                "user-42",
                new FindUserProfileAddressResponse("Amsterdam", "Keizersgracht")
            ),
            userProfile
        );
        assertEquals(42, userSettings.getUserId());
        assertTrue(userSettings.isNotificationsEnabled());
        assertEquals("dark", userSettings.getTheme());
    }

    @Test
    void rejectsDuplicateHandlersForSameQueryType() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> DefaultQueryDispatcher.builder()
                .register(FindUserNameQuery.class, new FindUserNameQueryHandler())
                .register(FindUserNameQuery.class, new DuplicateFindUserNameQueryHandler())
        );

        assertEquals(
            "Duplicate query handler for query type: io.easycqrs.core.query.findusername.FindUserNameQuery",
            exception.getMessage()
        );
    }

    @Test
    void rejectsQueryWithoutRegisteredHandler() {
        QueryDispatcher queryDispatcher = DefaultQueryDispatcher.builder().build();

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> queryDispatcher.dispatch(new UnknownQuery())
        );

        assertEquals(
            "No query handler registered for query type: io.easycqrs.core.query.unknownquery.UnknownQuery",
            exception.getMessage()
        );
    }

    @Test
    void builtDispatcherIsNotAffectedByLaterBuilderRegistrations() {
        DefaultQueryDispatcher.Builder builder = DefaultQueryDispatcher.builder()
            .register(FindUserNameQuery.class, new FindUserNameQueryHandler());
        QueryDispatcher queryDispatcher = builder.build();

        builder.register(FindUserAgeQuery.class, new FindUserAgeQueryHandler());

        assertEquals("user-42", queryDispatcher.dispatch(new FindUserNameQuery(42)));
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> queryDispatcher.dispatch(new FindUserAgeQuery(7))
        );

        assertEquals(
            "No query handler registered for query type: io.easycqrs.core.query.finduserage.FindUserAgeQuery",
            exception.getMessage()
        );
    }

    @Test
    void rejectsNullQuery() {
        QueryDispatcher queryDispatcher = DefaultQueryDispatcher.builder().build();

        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> queryDispatcher.dispatch(null)
        );

        assertEquals("query must not be null", exception.getMessage());
    }
}
