package xyz.slosa.skia;

import io.github.humbleui.skija.*;
import xyz.slosa.window.WindowEventHandler;

import java.util.function.Consumer;

import static io.github.humbleui.skija.FramebufferFormat.GR_GL_RGBA8;
import static io.github.humbleui.skija.SurfaceColorFormat.RGBA_8888;
import static io.github.humbleui.skija.SurfaceOrigin.BOTTOM_LEFT;
import static xyz.slosa.BakalariDesktopClient.HEIGHT;
import static xyz.slosa.BakalariDesktopClient.WIDTH;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public class SkiaRenderer {

    // Skia DirectContext to manage OpenGL state and operations
    private DirectContext context;

    // Skia Canvas used for drawing
    private Canvas canvas;

    // Skia Surface used for the drawing context
    private Surface surface;

    // Backend render target using OpenGL
    private BackendRenderTarget renderTarget;

    public SkiaRenderer(final WindowEventHandler windowEventHandler) {
        // Create a new DirectContext for OpenGL, note: needs to be initialized after OpenGL context is created
        context = DirectContext.makeGL();
        // Initial resize
        resizeSurface(WIDTH, HEIGHT);
        // Hook resizing
        windowEventHandler.resize(this::resizeSurface);
    }


    public void resizeSurface(final int width, final int height) {
        // Close existing render target and surface if they exist
        if (renderTarget != null) {
            renderTarget.close();
        }
        if (surface != null) {
            surface.close();
        }

        // Create a new BackendRenderTarget with OpenGL for the given width and height
        renderTarget = BackendRenderTarget.makeGL(width, height,
                0, 8, 0, GR_GL_RGBA8);

        // Create a Surface using the new BackendRenderTarget and bind it to the context
        surface = Surface.wrapBackendRenderTarget(context, renderTarget,
                BOTTOM_LEFT, RGBA_8888, ColorSpace.getSRGB());

        // Get the canvas from the surface for drawing operations
        canvas = surface.getCanvas();
    }

    public void render(final Consumer<Canvas> renderTask) {
        // Execute the render task on the canvas
        renderTask.accept(canvas);

        // Flush the context to apply changes
        context.flush();
    }
}