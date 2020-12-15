package sokobanfx.business;

import sokobanfx.model.Level;

public interface IPressurePad {
    boolean isPressureOn(Level level);
    void updatePressurePad(Level level);
}
