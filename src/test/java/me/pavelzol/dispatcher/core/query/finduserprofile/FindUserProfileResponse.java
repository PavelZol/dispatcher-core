package me.pavelzol.dispatcher.core.query.finduserprofile;

public record FindUserProfileResponse(long id, String name, FindUserProfileAddressResponse address) {
}
