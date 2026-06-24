package io.easycqrs.core.query.finduserage;

import io.easycqrs.query.Query;

public record FindUserAgeQuery(long userId) implements Query<Integer> {
}
