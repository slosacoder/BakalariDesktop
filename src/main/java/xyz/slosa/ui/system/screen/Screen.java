package xyz.slosa.ui.system.screen;


import xyz.slosa.ui.system.Node;
import xyz.slosa.ui.system.component.Component;

/**
 * @author slosa
 * @created 09.5.25, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public abstract class Screen extends Node<Screen, Component> {

    private boolean forceDraw;

    public Screen(final Screen parent) {
        super(parent);
    }

    public void onOpen(final Screen parent) {
        // Update parent
        this.parent = parent;
    }

    public abstract void onClose();

    @Override
    public void reposition(final int width, final int height) {
        this.children.forEach(child -> child.reposition(width, height));
    }

    protected void forceDraw() {
        forceDraw = true; // makes sure that the screen id being rendered EVEN when another screen is on top of it (good for transparent screens)
    }

    public boolean isForceDraw() {
        return forceDraw;
    }
}
