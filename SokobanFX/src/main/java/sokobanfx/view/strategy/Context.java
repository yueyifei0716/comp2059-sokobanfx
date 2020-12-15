package sokobanfx.view.strategy;

import javafx.scene.image.ImageView;

/**
 * Context class is used for choosing
 * the appropriate render strategy.
 */
public class Context {
    /**
     * The render strategy
     */
    private final RenderStrategy renderStrategy;

    /**
     * Sets the renderStrategy.
     *
     * @param renderStrategy the renderStrategy
     */
    public Context(RenderStrategy renderStrategy){
        this.renderStrategy = renderStrategy;
    }

    /**
     * Executes the strategy.
     *
     * @return the imageView of the corresponding strategy
     */
    public ImageView executeStrategy(){
        return renderStrategy.render();
    }
}

