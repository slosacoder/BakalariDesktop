package xyz.slosa.ui.system;

import io.github.humbleui.skija.Canvas;
import xyz.slosa.ui.system.interfaces.Drawable;
import xyz.slosa.ui.system.interfaces.Hoverable;
import xyz.slosa.ui.system.interfaces.Interactable;
import xyz.slosa.ui.system.interfaces.Positionable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slosa
 * @created 09.5.25, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public abstract class Node<Parent, Child extends Drawable & Interactable & Positionable & Hoverable> implements Drawable, Interactable, Positionable, Hoverable {

    protected Parent parent;
    protected final List<Child> children;

    public Node(final Parent parent) {
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    @Override
    public void draw(final Canvas canvas, final int width, final int height, final int mouseX, final int mouseY) {
        this.children.forEach(child -> child.draw(canvas, width, height, mouseX, mouseY));
    }

    @Override
    public boolean handleInput(final int width, final int height/*, Input input TODO :: IMPLEMENT */, final boolean pressed, final int mouseX, final int mouseY) {
        for (Child child : this.children) {
            if (child.handleInput(width, height,/* input, TODO :: IMPLEMENT */ pressed, mouseX, mouseY)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isHovered(final int width, final int height, final int mouseX, final int mouseY) {
        for (Child child : this.children) {
            if (child.isHovered(width, height, mouseX, mouseY)) {
                return true;
            }
        }

        return false;
    }

    public abstract void reposition(final int width, final int height);

    public Parent getParent() {
        return parent;
    }

    public List<Child> getChildren() {
        return children;
    }
}

