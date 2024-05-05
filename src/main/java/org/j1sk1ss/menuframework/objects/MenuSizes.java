package org.j1sk1ss.menuframework.objects;


public enum MenuSizes {
    OneLine(9),
    TwoLines(18),
    ThreeLines(27),
    FourLines(36),
    FiveLines(45),
    SixLines(54);

    public final int size;

    MenuSizes(int size) {
        this.size = size;
    }
}
