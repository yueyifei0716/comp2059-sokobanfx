package sokobanfx.view.strategy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * RenderStrategy class is used for
 * render the corresponding image.
 */
public interface RenderStrategy {
    /**
     * Render the object
     *
     * @return the imageView
     */
    ImageView render();

    /**
     * Sets the image in the grid pane
     *
     * @param image the image of the object
     * @return the imageView of the image
     */
    static ImageView setImage(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setImage(image);
        return imageView;
    }
}

