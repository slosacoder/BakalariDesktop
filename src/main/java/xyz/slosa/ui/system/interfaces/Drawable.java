package xyz.slosa.ui.system.interfaces;

import io.github.humbleui.skija.Canvas;

/**
 * @author slosa
 * @created 09.5.25, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
@FunctionalInterface
public interface Drawable {
    void draw(final Canvas canvas, final int width, final int height, final int mouseX, final int mouseY);
}
