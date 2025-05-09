package xyz.slosa.ui.system.interfaces;

/**
 * @author slosa
 * @created 09.5.25, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
@FunctionalInterface
public interface Interactable {
    boolean handleInput(final int width, final int height/*, Input input TODO :: implement*/, final boolean pressed, final int mouseX, final int mouseY);
}
