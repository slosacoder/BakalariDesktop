package xyz.slosa.objects.impl.login;

import xyz.slosa.objects.BakaObject;

public record LoginDataObject(String login, String password, String token) implements BakaObject {
}
