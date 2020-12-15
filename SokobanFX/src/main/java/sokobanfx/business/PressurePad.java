package sokobanfx.business;

import sokobanfx.model.GameGrid;
import sokobanfx.model.GameObject;
import sokobanfx.model.Level;

/**
 * PressurePad class is used to check the status
 * of the pressure pad and update the status of
 * the pressure pad.
 */
public class PressurePad implements IPressurePad {

    /**
     * Check if the pressure pad is active.
     *
     * @param currentLevel the currentLevel
     * @return true if there is crate on pressure pad, false otherwise
     */
    @Override
    public boolean isPressureOn(Level currentLevel) {
        GameGrid objectsGrid = currentLevel.getObjectsGrid();
        GameGrid potentialGrid = currentLevel.getPotentialGrid();
        for (int row = 0; row < objectsGrid.ROWS; row++) {
            for (int col = 0; col < objectsGrid.COLUMNS; col++) {
                // if crate is on diamond
                if (objectsGrid.getGameObjectAt(col, row) == GameObject.CRATE && potentialGrid.getGameObjectAt(col, row) == GameObject.PAD) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Updates the status of the pressure pad,
     * if not active, then gate is closed,
     * otherwise open.
     *
     * @param currentLevel the currentLevel
     */
    @Override
    public void updatePressurePad(Level currentLevel) {
        GameGrid objectsGrid = currentLevel.getObjectsGrid();
        if (isPressureOn(currentLevel)) {
            if (objectsGrid.getGameObjectAt(currentLevel.getGate()) != GameObject.KEEPER && objectsGrid.getGameObjectAt(currentLevel.getGate()) != GameObject.CRATE) {
                objectsGrid.removeGameObjectAt(currentLevel.getGate());
            }

        } else {
            currentLevel.getObjectsGrid().putGameObjectAt(GameObject.GATE, currentLevel.getGate());
        }
    }
}
