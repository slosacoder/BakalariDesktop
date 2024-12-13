package xyz.slosa;


import org.tinylog.Logger;
import org.tinylog.TaggedLogger;
import xyz.slosa.window.Window;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public class BakalariDesktopClient {

    // App version
    private static final BakalariDesktopVersion VERSION = new BakalariDesktopVersion();
    // Loggers
    public static final TaggedLogger LOGGER = Logger.tag("BakalariDesktop");
    // Window init
    private static final Window WINDOW = new Window(VERSION.getTitle(), 1000, 700);
    private static final TaggedLogger BAKA4J_LOGGER = Logger.tag("Baka4J");
    // Init BakalariAPI
    private static final BakalariAPI BAKALARI_API = new BakalariAPI(null, BAKA4J_LOGGER::info, BAKA4J_LOGGER::error);

    public static void main(String[] args) {
        // Startup logic...
        LOGGER.info("Starting... version: {}", VERSION);
        // Trying to create window
        LOGGER.warn("Trying to create window...");
    }
}