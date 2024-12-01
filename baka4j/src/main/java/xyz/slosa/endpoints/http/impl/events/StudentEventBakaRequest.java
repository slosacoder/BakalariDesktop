package xyz.slosa.endpoints.http.impl.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONObject;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
import xyz.slosa.objects.impl.absence.AbsenceDataObject;
import xyz.slosa.objects.impl.event.EventData;
import xyz.slosa.objects.impl.event.EventDetails;
import xyz.slosa.objects.impl.event.EventDetailsObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles the HTTP GET request for retrieving events data from the API.
 * This class allows retrieving all events, user-specific events, or public events,
 * and optionally filtering by a specific date.
 */
public class StudentEventBakaRequest extends AbstractBakaHttpGETRequest<EventDetailsObject> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * Constructs a request to retrieve events data.
     *
     * @param type the type of events to fetch (ALL, MY, PUBLIC)
     * @param date optional date parameter to filter events from a specific date (null for no filter)
     */
    public StudentEventBakaRequest(final EventType type, final LocalDateTime date) {
        super(type.getEndpoint() + (date != null ? "?from=" + DATE_FORMATTER.format(date) : ""));
    }

    /**
     * Deserializes the JSON response into an <code>EventsResponse</code>.
     *
     * @param jsonObject the JSON response object containing the events data
     * @return an <code>EventsResponse</code> containing the array of events
     */
    @Override
    public EventDetailsObject deserialize(final JSONObject jsonObject) {
        final JSONArray eventsArray = jsonObject.optJSONArray("Events");

        // Early exit if no events are present
        if (eventsArray == null || eventsArray.length() == 0) {
            return new EventDetailsObject(new EventDetails[0]);
        }

        final EventDetails[] events = new EventDetails[eventsArray.length()];

        for (int i = 0; i < eventsArray.length(); i++) {
            final JSONObject eventObj = eventsArray.optJSONObject(i);

            final JSONArray timesArray = eventObj.optJSONArray("Times");
            final EventData[] times = new EventData[timesArray != null ? timesArray.length() : 0];
            for (int j = 0; j < times.length; j++) {
                final JSONObject timeObj = timesArray.optJSONObject(j);
                times[j] = new EventData(
                        timeObj.optBoolean("WholeDay"),
                        LocalDateTime.parse(timeObj.optString("StartTime"), DATE_TIME_FORMATTER),
                        LocalDateTime.parse(timeObj.optString("EndTime"), DATE_TIME_FORMATTER)
                );
            }

            events[i] = new EventDetails(
                    eventObj.optString("Id"),
                    eventObj.optString("Title"),
                    eventObj.optString("Description"),
                    times,
                    eventObj.optJSONObject("EventType").optString("Name"),
                    eventObj.optString("DateChanged")
            );
        }

        return new EventDetailsObject(events);
    }

    @Override
    public EventDetailsObject deserialize(String json) throws JsonProcessingException {
        return getDeserializer().readValue(json, EventDetailsObject.class);
    }

    /**
     * Enum to represent the type of events to fetch.
     */
    public enum EventType {
        ALL("/api/3/events"),
        MY("/api/3/events/my"),
        PUBLIC("/api/3/events/public");

        private final String endpoint;

        EventType(final String endpoint) {
            this.endpoint = endpoint;
        }

        public String getEndpoint() {
            return endpoint;
        }
    }
}

