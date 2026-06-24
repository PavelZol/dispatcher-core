package me.pavelzol.dispatcher.core.query.findusername;

import me.pavelzol.dispatcher.core.api.Query;

public record FindUserNameQuery(long userId) implements Query<String> {
}
