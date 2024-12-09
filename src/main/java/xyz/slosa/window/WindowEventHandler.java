package xyz.slosa.window;

import static org.lwjgl.glfw.GLFW.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WindowEventHandler {
    private final Window window;

    public WindowEventHandler(final Window window) {
        this.window = window;
    }

    // Events
    public void resize(final BiConsumer<Integer, Integer> resizeAction) {
        glfwSetWindowSizeCallback(window.getWindowPointer(), (pointer, resizedWidth, resizedHeight) -> {
            // Execute the provided action when resize occurs
            resizeAction.accept(resizedWidth, resizedHeight);
        });
    }
    public void close(final Consumer<Void> closeAction) {
        glfwSetWindowCloseCallback(window.getWindowPointer(), (window) -> {
            closeAction.accept(null);  // Close action doesn't need parameters
        });
    }

}
