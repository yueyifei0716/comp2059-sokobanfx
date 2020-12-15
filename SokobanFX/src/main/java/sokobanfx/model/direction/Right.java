package sokobanfx.model.direction;

import java.awt.*;

public class Right implements MovingDirection {
    @Override
    public String getObject(Point delta) {
        if ((int) delta.getX() == 0 && (int) delta.getY() == 1) {
            return "RIGHT";
        }
        return "null";
    }
}
