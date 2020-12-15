package sokobanfx.view.strategy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PipeUpStrategy implements RenderStrategy {
    @Override
    public ImageView render() {
        Image image = new Image(getClass().getResource("/images/objects/pipeU.png").toString());
        return RenderStrategy.setImage(image);
    }
}
