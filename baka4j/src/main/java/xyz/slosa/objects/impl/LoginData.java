package xyz.slosa.objects.impl;

import xyz.slosa.objects.BakaObject;

public record LoginData(String login, String password, String token) implements BakaObject {
}
