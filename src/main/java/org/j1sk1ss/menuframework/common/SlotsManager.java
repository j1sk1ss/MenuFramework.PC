package org.j1sk1ss.menuframework.common;


import java.util.ArrayList;
import java.util.List;


public class SlotsManager {
    private static final int LineSize = 9;

    /**
     * Convert row and column in GUI to slot in inventory
     * @param row Row number
     * @param column Columns number
     * @return Slot in inventory
     */
    public static int coordinates2slot(int row, int column) {
        return row * LineSize + column;
    }

    /**
     * Convert coordinates to list of slots
     * @param firstRow Row number of first coordinate in GUI
     * @param firstColumn Column number of first coordinate in GUI
     * @param lastRow Row number of second coordinate in GUI
     * @param lastColumn Column number of second coordinate in GUI
     * @return List of slots, that include into bounds
     */
    public static List<Integer> coordinates2list(int firstRow, int firstColumn, int lastRow, int lastColumn) {
        return slots2list(coordinates2slot(firstRow, firstColumn), coordinates2slot(lastRow, lastColumn));
    }

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
