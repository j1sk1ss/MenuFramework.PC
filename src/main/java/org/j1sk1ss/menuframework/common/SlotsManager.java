package org.j1sk1ss.menuframework.common;

import java.util.ArrayList;
import java.util.List;


public class SlotsManager {
    /**
     * Convert two bounds to list of slots
     * @param firstSlot First bound
     * @param secondSlot Second bound
     * @return List of slots, that include into bounds
     */
    public static List<Integer> slots2list(int firstSlot, int secondSlot) {
        var list = new ArrayList<Integer>();
        var secondCoordinate = secondSlot - firstSlot;

        var height = (secondCoordinate / 9) + 1;
        var weight = (secondCoordinate % 9) + 1;
        for (var i = firstSlot / 9; i < firstSlot / 9 + height; i++)
            for (var j = firstSlot % 9; j < firstSlot % 9 + weight; j++)
                list.add(9 * i + j);

        return list;
    }
}
