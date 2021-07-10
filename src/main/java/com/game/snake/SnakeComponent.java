package com.game.snake;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class SnakeComponent extends Component {

    private static final String PREV_POS = "prevPos";
    private Point2D direction = new Point2D(0, 0);
    List<Entity> snakeBodyList = new ArrayList<>();
    private final AnimatedTexture texture;
    private final AnimationChannel animWalk;

    public SnakeComponent() {

        var imageOpen = image("snakehead.png");
        var animIdle = new AnimationChannel(imageOpen, 2, 40 / 2, 25, Duration.millis(1), 0, 0);
        animWalk = new AnimationChannel(imageOpen, 2, 40 / 2, 25, Duration.millis(1), 0, 1);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        snakeBodyList.add(entity);
        entity.setProperty(PREV_POS, entity.getPosition());
    }

    @Override
    public void onUpdate(double tpf) {
        if (texture.getAnimationChannel() != animWalk) {
            texture.loopAnimationChannel(animWalk);
        }
        entity.setProperty(PREV_POS, entity.getPosition());
        entity.translate(direction.multiply(20));
        checkForBounds();
        for (var i = 1; i < snakeBodyList.size(); i++) {
            var prevPart = snakeBodyList.get(i - 1);
            var part = snakeBodyList.get(i);
            Point2D prevPos = prevPart.getObject(PREV_POS);
            part.setProperty(PREV_POS, part.getPosition());
            part.setPosition(prevPos);
        }
        die();
    }

    private void checkForBounds() {
        if (entity.getX() <= 0) {
            final double y = entity.getY();
            entity.setPosition(getAppWidth() - 1.0, y);
        }
        if (entity.getX() >= getAppWidth()) {
            final double y = entity.getY();
            entity.setPosition(0, y);
        }
        if (entity.getY() <= 0) {
            final double x = entity.getX();
            entity.setPosition(x, getAppHeight() - 1.0);
        }
        if (entity.getY() >= getAppHeight()) {
            final double x = entity.getX();
            entity.setPosition(x, 0);
        }
    }


    public void die() {
        final Point2D currentPosition = snakeBodyList.get(0).getPosition();
        for (var i = 1; i < snakeBodyList.size(); i++) {
            Entity snakeBody = snakeBodyList.get(i);
            final Point2D snakeBodyPosition = snakeBody.getPosition();
            if (currentPosition.equals(snakeBodyPosition)) {
                getDialogService().showMessageBox("Game Over!! You cannot eat your self!!", () -> getGameController().startNewGame());
            }
        }
    }

    public void up() {
        direction = new Point2D(0, -1);
    }

    public void down() {
        direction = new Point2D(0, 1);
    }

    public void left() {
        direction = new Point2D(-1, 0);
    }

    public void right() {
        direction = new Point2D(1, 0);
    }

    public void grow() {
        var lastBodyPart = snakeBodyList.get(snakeBodyList.size() - 1);
        final Point2D prevPos = lastBodyPart.getObject(PREV_POS);
        var body = FXGL.spawn("snake", prevPos);
        snakeBodyList.add(body);
    }
}
