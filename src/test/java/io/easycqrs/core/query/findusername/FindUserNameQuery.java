package io.easycqrs.core.query.findusername;

import io.easycqrs.core.api.Query;

public record FindUserNameQuery(long userId) implements Query<String> {
}
