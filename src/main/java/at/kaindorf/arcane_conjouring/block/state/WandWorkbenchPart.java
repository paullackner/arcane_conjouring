package at.kaindorf.arcane_conjouring.block.state;

import net.minecraft.util.StringRepresentable;

public enum WandWorkbenchPart implements StringRepresentable {
    HEAD("head"),
    FOOT("foot");


    private final String name;

    WandWorkbenchPart(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return null;
    }
}
