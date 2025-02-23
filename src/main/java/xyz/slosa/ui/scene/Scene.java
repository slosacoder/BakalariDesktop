package xyz.slosa.ui.scene;


import io.github.humbleui.skija.Canvas;
import xyz.slosa.BakalariDesktopClient;
import xyz.slosa.window.WindowEventHandler;

import static xyz.slosa.BakalariDesktopClient.*;

public abstract class Scene {

    private int width, height;

    public Scene(final WindowEventHandler windowEventHandler) {
        LOGGER.info("Displaying scene \"{}\"", getClass().getSimpleName());
        // Set default
        this.width = WIDTH;
        this.height = HEIGHT;
        // Update size
        windowEventHandler.resize(this::setDimensions);
    }

    public abstract void render(final Canvas canvas);

    protected int getWidth() {
        return width;
    }

    protected int getHeight() {
        return height;
    }

    private void setDimensions(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

}
