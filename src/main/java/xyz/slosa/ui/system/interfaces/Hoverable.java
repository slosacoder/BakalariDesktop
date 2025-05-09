package xyz.slosa.ui.system.interfaces;

/**
 * @author slosa
 * @created 09.5.25, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
@FunctionalInterface
public interface Hoverable {
    boolean isHovered(final int width, final int height, final int mouseX, final int mouseY);
}
