package xyz.slosa.ui.system.screen;

import io.github.humbleui.skija.Canvas;
import xyz.slosa.commons.Handler;
import xyz.slosa.window.WindowEventHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

/**
 * @author slosa
 * @created 09.5.25, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public final class ScreenHandler implements Handler<ScreenFactory> {

    private final Map<String, ScreenFactory> screens = new HashMap<>();
    private final Map<String, Screen> screenCache = new HashMap<>();
    private final Stack<Screen> screenStack = new Stack<>();
    private int width, height;
    private double mouseX, mouseY;

    public ScreenHandler(final WindowEventHandler windowEventHandler) {
        // hook resize
        windowEventHandler.resize(this::resize);
        // hook mouse coords
        windowEventHandler.mouseMove((mouseX, mouseY) -> {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        });
    }

    @Override
    public Map<String, ScreenFactory> map() {
        return screens;
    }

    public void drawScreens(final Canvas canvas) {
        screenStack.forEach(screen -> screen.draw(canvas, width, height, mouseX, mouseY));
    }

    private void resize(final int width, final int height) {
        screenStack.forEach(screen -> screen.reposition(width, height));
        this.width = width;
        this.height = height;
    }

    public void display(final String screen) {
        final Screen parent = screenStack.empty() ? null : screenStack.peek();

        // retrieve from cache or create if not present
        final Screen cached = screenCache.computeIfAbsent(screen, key -> {
            // create screen
            final Optional<ScreenFactory> factory = match(key);
            return factory.map(screenFactory -> screenFactory.create(parent))
                    .orElse(null);

        });

        if (cached == null) return;
        // push the new screen onto the stack
        screenStack.push(cached);
        // call on opened
        cached.onOpen(parent);
        // position screen elements
        cached.reposition(width, height);

        // stop drawing unless it is desired
        if (!parent.isForceDraw()) {
            screenStack.remove(parent);
        }

    }
    public void close() {
        final Screen screen = screenStack.pop();
        // call on close
        screen.onClose();
    }

    public boolean isScreenOpen() {
        return !screenStack.empty();
    }
}
