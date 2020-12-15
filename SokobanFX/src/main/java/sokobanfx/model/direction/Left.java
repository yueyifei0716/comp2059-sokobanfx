package sokobanfx.model.direction;

import java.awt.*;

public class Left implements MovingDirection {
    @Override
    public String getObject(Point delta) {
        if ((int) delta.getX() == 0 && (int) delta.getY() == -1) {
            return "LEFT";
        }
        return "null";
    }
}
