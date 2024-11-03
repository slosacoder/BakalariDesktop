package xyz.slosa.objects;

/**
 * @author : slosa
 * @created : 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public record ErrorObject() implements BakaObject {

    @Override
    public String status() {
        return "You have accessed a dummy ErrorObject, this shouldn't happen!";
    }
}
