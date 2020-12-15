package sokobanfx.model.direction;

import java.awt.*;

public class Down implements MovingDirection {
    @Override
    public String getObject(Point delta) {
        if ((int) delta.getX() == 1 && (int) delta.getY() == 0) {
            return "DOWN";
        }
        return "null";
    }
}
