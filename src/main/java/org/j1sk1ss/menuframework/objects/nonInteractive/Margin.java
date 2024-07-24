package org.j1sk1ss.menuframework.objects.nonInteractive;

import lombok.Getter;
import lombok.Setter;
import org.j1sk1ss.menuframework.common.SlotsManager;

import java.util.List;


@Getter @Setter
public class Margin {
    public Margin(int row, int col) {
        Row = row;
        Col = col;

        Height = -1;
        Width  = -1;
    }

    public Margin(int row, int col, int height, int width) {
        Row = row;
        Col = col;
        Height = height;
        Width  = width;
    }

    private int Row;
    private int Col;
    private int Height;
    private int Width;

    /**
     * Move margin
     * @param margin Margin for move
     */
    public void Move(Margin margin) {
        Row += margin.getRow();
        Col += margin.getCol();
    }

    /**
     * Convert margin to list of slots
     * @return List of slots, that include into bounds
     */
    public List<Integer> toSlots() {
        var firstCoordinate = SlotsManager.coordinates2slot(Row, Col);
        var lastCoordinate = SlotsManager.coordinates2slot(Row + Height, Col + Width);
        return SlotsManager.slots2list(firstCoordinate, lastCoordinate);
    }
}
