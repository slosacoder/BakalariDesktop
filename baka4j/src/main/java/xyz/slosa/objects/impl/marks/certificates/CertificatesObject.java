package xyz.slosa.objects.impl.marks.certificates;

import xyz.slosa.objects.BakaObject;

public record CertificatesObject(
        CertificateTerm[] certificateTerms
) implements BakaObject {
}
