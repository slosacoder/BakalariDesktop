package xyz.slosa.ui.system.screen;

/**
 * @author slosa
 * @created 09.5.25, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
@FunctionalInterface
public interface ScreenFactory {
    Screen create(Screen parent);
}
