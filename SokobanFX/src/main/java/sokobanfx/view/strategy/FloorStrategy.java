package sokobanfx.view.strategy;

import javafx.scene.image.ImageView;

public class FloorStrategy implements RenderStrategy {
    @Override
    public ImageView render() {
        return RenderStrategy.setImage(null);
    }
}
