package xyz.slosa.endpoints.http.impl.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
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
     * @param json the JSON response object containing the events data
     * @return an <code>EventDetailsObject</code> containing the array of events
     */
    @Override
    public EventDetailsObject deserialize(final String json) throws JsonProcessingException {
        return deserializer().readValue(json, EventDetailsObject.class);
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

