import xyz.slosa.endpoints.http.BakalariAPI;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public class Main {
    public static void main(String[] args) {
        try {
            BakalariAPI api = new BakalariAPI("https://www.gymnaziumjihlava.cz:81/zak", System.out::println);
            api.authenticate("username", "password").join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
