package me.pavelzol.dispatcher.core.query;

import me.pavelzol.dispatcher.core.dispatcher.BasicQueryDispatcherImpl;
import me.pavelzol.dispatcher.core.dispatcher.QueryDispatcher;
import me.pavelzol.dispatcher.core.query.finduserage.FindUserAgeQuery;
import me.pavelzol.dispatcher.core.query.finduserage.FindUserAgeQueryHandler;
import me.pavelzol.dispatcher.core.query.findusername.DuplicateFindUserNameQueryHandler;
import me.pavelzol.dispatcher.core.query.findusername.FindUserNameQuery;
import me.pavelzol.dispatcher.core.query.findusername.FindUserNameQueryHandler;
import me.pavelzol.dispatcher.core.query.finduserprofile.FindUserProfileAddressResponse;
import me.pavelzol.dispatcher.core.query.finduserprofile.FindUserProfileQuery;
import me.pavelzol.dispatcher.core.query.finduserprofile.FindUserProfileQueryHandler;
import me.pavelzol.dispatcher.core.query.finduserprofile.FindUserProfileResponse;
import me.pavelzol.dispatcher.core.query.findusersettings.FindUserSettingsQuery;
import me.pavelzol.dispatcher.core.query.findusersettings.FindUserSettingsQueryHandler;
import me.pavelzol.dispatcher.core.query.findusersettings.FindUserSettingsResponse;
import me.pavelzol.dispatcher.core.query.unknownquery.UnknownQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasicQueryDispatcherImplTest {

    @Test
    void dispatchesQueryToRegisteredHandlerAndReturnsResponse() {
        QueryDispatcher queryDispatcher = BasicQueryDispatcherImpl.builder()
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
        assertEquals(true, userSettings.isNotificationsEnabled());
        assertEquals("dark", userSettings.getTheme());
    }

    @Test
    void rejectsDuplicateHandlersForSameQueryType() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> BasicQueryDispatcherImpl.builder()
                .register(FindUserNameQuery.class, new FindUserNameQueryHandler())
                .register(FindUserNameQuery.class, new DuplicateFindUserNameQueryHandler())
        );

        assertEquals(
            "Duplicate query handler for query type: me.pavelzol.dispatcher.core.query.findusername.FindUserNameQuery",
            exception.getMessage()
        );
    }

    @Test
    void rejectsQueryWithoutRegisteredHandler() {
        QueryDispatcher queryDispatcher = BasicQueryDispatcherImpl.builder().build();

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> queryDispatcher.dispatch(new UnknownQuery())
        );

        assertEquals(
            "No query handler registered for query type: me.pavelzol.dispatcher.core.query.unknownquery.UnknownQuery",
            exception.getMessage()
        );
    }

    @Test
    void builtDispatcherIsNotAffectedByLaterBuilderRegistrations() {
        BasicQueryDispatcherImpl.Builder builder = BasicQueryDispatcherImpl.builder()
            .register(FindUserNameQuery.class, new FindUserNameQueryHandler());
        QueryDispatcher queryDispatcher = builder.build();

        builder.register(FindUserAgeQuery.class, new FindUserAgeQueryHandler());

        assertEquals("user-42", queryDispatcher.dispatch(new FindUserNameQuery(42)));
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> queryDispatcher.dispatch(new FindUserAgeQuery(7))
        );

        assertEquals(
            "No query handler registered for query type: me.pavelzol.dispatcher.core.query.finduserage.FindUserAgeQuery",
            exception.getMessage()
        );
    }

    @Test
    void rejectsNullQuery() {
        QueryDispatcher queryDispatcher = BasicQueryDispatcherImpl.builder().build();

        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> queryDispatcher.dispatch(null)
        );

        assertEquals("query must not be null", exception.getMessage());
    }
}
