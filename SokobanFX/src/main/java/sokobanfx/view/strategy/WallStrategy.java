package sokobanfx.view.strategy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WallStrategy implements RenderStrategy {
    @Override
    public ImageView render() {
        Image image = new Image(getClass().getResource("/images/objects/wall.png").toString());
        return RenderStrategy.setImage(image);
    }
}
