package org.j1sk1ss.menuframework.objects;


import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private static final Map<Integer, MenuSizes> map;
    static {
        map = Arrays.stream(values()).collect(Collectors.toMap(e -> e.size, e -> e));
    }

    public static MenuSizes fromInt(int value) {
        return Optional.ofNullable(map.get(value)).orElse(OneLine);
    }
}
