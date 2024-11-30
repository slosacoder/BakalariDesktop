package xyz.slosa.objects.impl.gdpr;

import xyz.slosa.objects.BakaObject;

public record GdprCommissionersObject(
        GdprCommissioner[] commissioners
) implements BakaObject {
}
