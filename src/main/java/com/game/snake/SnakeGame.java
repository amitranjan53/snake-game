package com.game.snake;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.game.snake.EntityTypes.*;

public class SnakeGame extends GameApplication {

    private Entity snake;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.UP, () -> this.snake.getComponentOptional(SnakeComponent.class).ifPresent(SnakeComponent::up));
        onKey(KeyCode.DOWN, () -> this.snake.getComponentOptional(SnakeComponent.class)
                                            .ifPresent(SnakeComponent::down));
        onKey(KeyCode.LEFT, () -> this.snake.getComponentOptional(SnakeComponent.class)
                                            .ifPresent(SnakeComponent::left));
        onKey(KeyCode.RIGHT, () -> this.snake.getComponentOptional(SnakeComponent.class)
                                             .ifPresent(SnakeComponent::right));
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.BLACK);
        getGameWorld().addEntityFactory(new SnakeGameFactory());
        this.snake = spawn("snakehead", getAppWidth() / 2, getAppHeight() / 2 - 30);
        spawn("food", FXGL.random(20, getAppWidth() - 20), FXGL.random(20, getAppHeight() - 20));
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(SNAKEHEAD, FOOD) {
            @Override
            protected void onCollision(Entity player, Entity food) {
                food.removeFromWorld();
            }
        });
    }

    @Override
    protected void onUpdate(double tpf) {
        if (getGameWorld().getEntitiesByType(EntityTypes.FOOD).size() != 1) {
            spawn("food", FXGL.random(20, getAppWidth() - 20), FXGL.random(20, getAppHeight() - 20));
            snake.getComponent(SnakeComponent.class).grow();
        }
    }

//    @Override
//    protected void initUI() {
//        Text textPixels = new Text();
//        textPixels.setTranslateX(50); // x = 50
//        textPixels.setTranslateY(100); // y = 100
//
//        getGameScene().addUINode(textPixels); // add to the scene graph
//    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Snake Game : Mahi!! I <3 u sona");
        gameSettings.setIntroEnabled(false); // turn off intro
        gameSettings.setVersion("1.0");
        gameSettings.setTicksPerSecond(5);
        gameSettings.setFullScreenAllowed(true);
        gameSettings.setManualResizeEnabled(true);
        gameSettings.setPreserveResizeRatio(false);
        gameSettings.setGameMenuEnabled(true);
    }


}
