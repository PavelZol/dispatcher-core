package io.easycqrs.core.query.finduserage;

import io.easycqrs.core.api.Query;

public record FindUserAgeQuery(long userId) implements Query<Integer> {
}
