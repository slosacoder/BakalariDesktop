package xyz.slosa.ui.system.component;

import xyz.slosa.ui.system.Node;

/**
 * @author slosa
 * @created 09.5.25, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public abstract class Component extends Node<Component, Component> {

    private int x, y, width, height;

    public Component(final Component parent) {
        super(parent);
    }

    @Override
    public void reposition(final int width, final int height) {
        this.children.forEach(child -> child.reposition(this.width, this.height));
    }

    public static boolean isHovered(final float x, final float y, final float width, final float height, final int mouseX, final int mouseY) {
        return mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + height;
    }

    @Override
    public boolean isHovered(final int width, final int height, final int mouseX, final int mouseY) {
        return Component.isHovered(this.getX(), this.getY(), this.getWidth(), this.getHeight(), mouseX, mouseY) ||
            super.isHovered(width, height, mouseX, mouseY);
    }

    public int getX() {
        int x = this.x;
        Component cp = this.parent;

        while (cp != null) {
            x += cp.x;
            cp = cp.parent;
        }

        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        int y = this.y;
        Component cp = this.parent;

        while (cp != null) {
            y += cp.y;
            cp = cp.parent;
        }

        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }
}
