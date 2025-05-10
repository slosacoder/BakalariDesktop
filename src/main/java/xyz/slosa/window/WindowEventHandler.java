package xyz.slosa.window;

import java.util.function.BiConsumer;

import static org.lwjgl.glfw.GLFW.*;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
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

    // Mouse move event
    public void mouseMove(final BiConsumer<Double, Double> mouseMoveAction) {
        glfwSetCursorPosCallback(window.getWindowPointer(), (pointer, xpos, ypos) -> {
            // Execute the provided action with the new mouse coordinates
            mouseMoveAction.accept(xpos, ypos);
        });
    }
}
