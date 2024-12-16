package xyz.slosa.util.account;


import xyz.slosa.BakalariAPI;
import xyz.slosa.BakalariDesktopClient;

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
