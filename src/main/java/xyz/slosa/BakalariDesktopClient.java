package xyz.slosa;


import org.tinylog.Logger;
import org.tinylog.TaggedLogger;
import xyz.slosa.skia.SkiaRenderer;
import xyz.slosa.ui.system.screen.ScreenHandler;
import xyz.slosa.window.Window;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public class BakalariDesktopClient {
    // Default window size
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 900;
    // App version
    public static final BakalariDesktopVersion VERSION = new BakalariDesktopVersion();
    // Loggers
    public static final TaggedLogger LOGGER = Logger.tag("BakalariDesktop");
    // Window init
    public static final Window WINDOW = new Window(VERSION.getTitle(), 1000, 700);
    public static final TaggedLogger BAKA4J_LOGGER = Logger.tag("Baka4J");
    // Skia Renderer
    private static final SkiaRenderer SKIA_RENDERER = new SkiaRenderer(WINDOW.getWindowEventHandler());
    // Handlers
    private static final ScreenHandler SCREEN_HANDLER = new ScreenHandler(WINDOW.getWindowEventHandler());

    public static void main(String[] args) {
        // Startup logic...
        LOGGER.info("Starting... build: {}", VERSION);
        // Trying to create window
        LOGGER.warn("Trying to create window...");
        // Start Rendering
        WINDOW.loop(new Runnable() {
            @Override
            public void run() {
                SKIA_RENDERER.render(canvas -> {
                    SCREEN_HANDLER.drawScreens(canvas); // perform actual drawing
                });
            }
        });
    }
}