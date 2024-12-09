package xyz.slosa.window;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    // Basic variables for tracking
    private final String title;
    private int width, height;
    private final long windowPointer;
    // Window Events
    private final WindowEventHandler windowEventHandler;

    public Window(final String title, final int width, final int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        windowPointer = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowPointer == NULL)
            throw new RuntimeException("Failed to create the GLFW window");


        // Get the thread stack and push a new frame
        try (final MemoryStack stack = stackPush()) {
            final IntBuffer pWidth = stack.mallocInt(1); // int*
            final IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowPointer, pWidth, pHeight);

            // Get the resolution of the primary monitor
            final GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    windowPointer,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowPointer);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(windowPointer);

        // Create window Events
        windowEventHandler = new WindowEventHandler(this);
    }


    public long getWindowPointer() {
        return windowPointer;
    }

    public String getTitle() {
        return title;
    }

    public WindowEventHandler getWindowEventHandler() {
        return windowEventHandler;
    }

    public void loop(final Runnable execution) {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Run the rendering loop until the user has attempted to close
        while ( !glfwWindowShouldClose(windowPointer) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            // Do the actual drawing
            execution.run();
            glfwSwapBuffers(windowPointer); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
}
