package dev.exvaccum.bert.control;

import dev.exvaccum.bert.world.interfaces.pc.MetaWindow;

public class WButton extends MButton{

    public MetaWindow ownerWindow;

    /**
     * Creates a button with specified dimensions, for use inside of a metawindow rather than an interface
     *
     * @param x      X coordinate
     * @param y      Y coordinate
     * @param width  width
     * @param height height
     * @param owner
     */
    public WButton(int x, int y, int width, int height, MetaWindow owner) {
        super(x, y, width, height, owner.owner);
        ownerWindow = owner;
    }


}
