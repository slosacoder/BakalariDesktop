package xyz.slosa.endpoints.http.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.json.JSONObject;
import xyz.slosa.objects.BakaObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Abstract base class for handling HTTP requests to the Bakalari API.
 * This class defines how requests should be processed and deserialized into specific objects.
 *
 * @param <T> The type of the object that will hold the deserialized data.
 */
public abstract class AbstractBakaHttpRequest<T extends BakaObject> {
    private final String endpoint;
    private T object;

    /**
     * Constructor to initialize the endpoint for the HTTP request.
     *
     * @param endpoint the API endpoint for this request
     */
    public AbstractBakaHttpRequest(final String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * This method should populate the `object` field with the deserialized data from the JSON object.
     * It can also return the deserialized object if needed.
     *
     * @param jsonObject the JSON object containing the data to be deserialized
     * @return the deserialized object of type O
     */
    @Deprecated
    public abstract T deserialize(final JSONObject jsonObject);
    
    public abstract T deserialize(final String json) throws JsonProcessingException;

    /**
     * Returns the deserialized object data, if it exists.
     *
     * @return the deserialized object of type O
     * @throws NullPointerException if the object has not been deserialized (i.e., is null)
     */
    public T getData() {
        if (object == null)
            throw new NullPointerException("There was an error while issuing the request: " + getClass().getSimpleName() + ", hash(" + hashCode() + ")");
        return object; // Return the actual object data on success
    }

    public void fillData(T object) {
        this.object = object;
    }

    /**
     * Returns the API endpoint associated with this request.
     *
     * @return the API endpoint string
     */
    public String getEndpoint() {
        return endpoint;
    }
    
    protected ObjectMapper getDeserializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule().addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME)));
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        return mapper;
    }
}