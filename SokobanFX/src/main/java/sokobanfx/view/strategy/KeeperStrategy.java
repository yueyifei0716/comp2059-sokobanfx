package sokobanfx.view.strategy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sokobanfx.business.GameEngine;

public class KeeperStrategy implements RenderStrategy {
    @Override
    public ImageView render() {
        String playerDirection = GameEngine.getCurrentLevel().getPlayerDirection();
        Image image = new Image((getClass().getResource("/images/objects/").toString()) + "player" + playerDirection + ".png");
        return RenderStrategy.setImage(image);
    }
}
