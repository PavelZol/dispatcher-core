package io.easycqrs.core.query.findusername;

import io.easycqrs.query.Query;

public record FindUserNameQuery(long userId) implements Query<String> {
}
