package me.pavelzol.dispatcher.core.query.finduserage;

import me.pavelzol.dispatcher.core.api.Query;

public record FindUserAgeQuery(long userId) implements Query<Integer> {
}
