package sokobanfx.model.direction;

public class DirectionFactory {
    /**
     * @param direction the player direction
     * @return the object towards the player
     */
    public MovingDirection getDirection(String direction) {
        if (direction == null){
            return null;
        }
        if (direction.equalsIgnoreCase("UP")){
            return new Up();
        } else if (direction.equalsIgnoreCase("DOWN")){
            return new Down();
        } else if (direction.equalsIgnoreCase("RIGHT")){
            return new Right();
        } else if (direction.equalsIgnoreCase("LEFT")){
            return new Left();
        }
        return null;
    }
}
