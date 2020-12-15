package sokobanfx.view.strategy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TrapMStrategy implements RenderStrategy {
    @Override
    public ImageView render() {
        Image image = new Image(getClass().getResource("/images/objects/monster_trap.png").toString());
        return RenderStrategy.setImage(image);
    }
}
