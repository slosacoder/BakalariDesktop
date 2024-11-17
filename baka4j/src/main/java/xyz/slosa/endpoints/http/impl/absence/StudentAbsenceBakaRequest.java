package xyz.slosa.endpoints.http.impl.absence;

import org.json.JSONObject;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
import xyz.slosa.objects.impl.absence.AbsenceDataObject;

public class StudentAbsenceBakaRequest extends AbstractBakaHttpGETRequest<AbsenceDataObject> {

    /**
     * Constructor to initialize the endpoint for the HTTP GET request.
     */
    public StudentAbsenceBakaRequest() {
        super("/api/3/absence/student");
    }

    @Override
    public AbsenceDataObject deserialize(JSONObject jsonObject) {
        return null;
    }
}
