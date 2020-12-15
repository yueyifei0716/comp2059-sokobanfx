package sokobanfx.view;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sokobanfx.business.GameLogger;
import sokobanfx.model.GameObject;
import sokobanfx.view.strategy.*;

/**
 * RenderObject class is used to render
 * each object based on strategy pattern.
 */

public class RenderObjects extends Rectangle {

    /**
     * Gets the imageView of a GameObject
     *
     * @param object the GameObject
     * @return an ImageView
     */
    public ImageView getObjectImage(GameObject object) {
        ImageView imageView;
        Context context;

        switch (object) {
            case WALL:
                context = new Context(new WallStrategy());
                imageView = context.executeStrategy();
                break;

            case BOUNDARY:
                context = new Context(new BoundaryStrategy());
                imageView = context.executeStrategy();
                break;

            case CRATE:
                context = new Context(new CrateStrategy());
                imageView = context.executeStrategy();
                break;

            case TRAP_M:
                context = new Context(new TrapMStrategy());
                imageView = context.executeStrategy();
                break;

            case TRAP_R:
                context = new Context(new TrapRStrategy());
                imageView = context.executeStrategy();
                break;

            case DIAMOND:
                if (GameLogger.isDebugActive()) {
                    FadeTransition ft = new FadeTransition(Duration.millis(1000), this);
                    ft.setFromValue(1.0);
                    ft.setToValue(0.2);
                    ft.setCycleCount(Timeline.INDEFINITE);
                    ft.setAutoReverse(true);
                    ft.play();
                }
                context = new Context(new DiamondStrategy());
                imageView = context.executeStrategy();
                break;

            case CRATE_ON_DIAMOND:
                context = new Context(new CrateOnDiamondStrategy());
                imageView = context.executeStrategy();
                break;

            case PAD:
                context = new Context(new PadStrategy());
                imageView = context.executeStrategy();
                break;

            case GATE:
                context = new Context(new GateStrategy());
                imageView = context.executeStrategy();
                break;

            case CRATE_ON_PAD:
                context = new Context(new CrateOnPadStrategy());
                imageView = context.executeStrategy();
                break;

            case KEEPER:
                context = new Context(new KeeperStrategy());
                imageView = context.executeStrategy();
                break;

            case FLOOR:
                context = new Context(new FloorStrategy());
                imageView = context.executeStrategy();
                break;

            case PIPE_UP:
                context = new Context(new PipeUpStrategy());
                imageView = context.executeStrategy();
                break;

            case PIPE_DOWN:
                context = new Context(new PipeDownStrategy());
                imageView = context.executeStrategy();
                break;

            case PIPE_LEFT:
                context = new Context(new PipeLeftStrategy());
                imageView = context.executeStrategy();
                break;

            case PIPE_RIGHT:
                context = new Context(new PipeRightStrategy());
                imageView = context.executeStrategy();
                break;

            default:
                String message = "Error in Level constructor. Object not recognized.";
                GameLogger.showSevere(message);
                throw new AssertionError(message);
        }

        imageView.setFitHeight(40);
        imageView.setFitWidth(35);

        if (GameLogger.isDebugActive()) {
            this.setStroke(Color.RED);
            this.setStrokeWidth(0.25);
        }

        return imageView;
    }

}
