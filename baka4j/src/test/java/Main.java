import xyz.slosa.BakalariAPI;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public class Main {
    public static void main(String[] args) {
        BakalariAPI api = new BakalariAPI("https://www.gymnaziumjihlava.cz:8081", System.out::println);
    }
}
