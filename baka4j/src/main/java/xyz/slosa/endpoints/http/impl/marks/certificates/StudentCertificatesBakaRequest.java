package xyz.slosa.endpoints.http.impl.marks.certificates;

import com.fasterxml.jackson.core.JsonProcessingException;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
import xyz.slosa.objects.impl.marks.certificates.CertificatesObject;

import java.time.format.DateTimeFormatter;

public class StudentCertificatesBakaRequest extends AbstractBakaHttpGETRequest<CertificatesObject> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * Constructs a request to retrieve final certificate marks.
     * Requires a valid access token in the header.
     */
    public StudentCertificatesBakaRequest() {
        super("/api/3/marks/final");
    }

    /**
     * Deserializes the JSON response into a CertificatesObject.
     *
     * @param json the JSON response object containing certificate marks data
     * @return a {@link CertificatesObject} containing the final marks
     */

    @Override
    public CertificatesObject deserialize(final String json) throws JsonProcessingException {
        return deserializer().readValue(json, CertificatesObject.class);
    }
}
