package xyz.slosa.baka.account;


import xyz.slosa.BakalariAPI;
import xyz.slosa.BakalariDesktopClient;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public class Account {

    private final String login, password, schoolURL;

    private final BakalariAPI bakalariAPI;

    public Account(final String login, final String password, final String schoolURL) {
        this.login = login;
        this.password = password;
        this.schoolURL = schoolURL;

        // Init BakaAPI
        bakalariAPI = new BakalariAPI(schoolURL, BakalariDesktopClient.BAKA4J_LOGGER::info, BakalariDesktopClient.BAKA4J_LOGGER::error);
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getSchoolURL() {
        return schoolURL;
    }

    public BakalariAPI bakaAPI() {
        return bakalariAPI;
    }
}
