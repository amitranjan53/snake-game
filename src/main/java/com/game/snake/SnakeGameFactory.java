package com.game.snake;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.game.snake.EntityTypes.FOOD;
import static com.game.snake.EntityTypes.SNAKE;

public class SnakeGameFactory implements EntityFactory {

    @Spawns("snake")
    public Entity mahiPlaying(SpawnData mahiInit) {
        var center = new Circle(0, 0, Color.RED.getRed());
        center.setRadius(10);
        center.setFill(Color.RED);
        center.setStroke(Color.RED);
        return entityBuilder(mahiInit).type(SNAKE)
                                      .bbox(new HitBox(BoundingShape.circle(.1)))
                                      .with(new SnakeComponent(), new CollidableComponent(true))
                                      .view(center)
                                      .build();
    }

    @Spawns("food")
    public Entity mahiFood(SpawnData mahiInit) {
        var center = new Rectangle();
        center.setWidth(20);
        center.setHeight(20);
        center.setFill(Color.GREEN);
        return entityBuilder(mahiInit).type(FOOD)
                                      .viewWithBBox(center)
                                      .with(new FoodComponent(), new CollidableComponent(true))
                                      .build();
    }
}
