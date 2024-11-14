import xyz.slosa.BakalariAPI;
import xyz.slosa.endpoints.http.impl.login.LoginWithCredentialsBakaRequest;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public class Main {
    public static void main(String[] args) {
        try {
            BakalariAPI api = new BakalariAPI("https://www.gymnaziumjihlava.cz:81/zak", System.out::println);
            System.out.println(api.requestPost(new LoginWithCredentialsBakaRequest("test", "test")).id)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
