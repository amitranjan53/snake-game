module SnakeGame {
    requires com.almasb.fxgl.all;
    opens assets.textures;
    opens com.game.snake to com.almasb.fxgl.all;
    exports com.game.snake;
}