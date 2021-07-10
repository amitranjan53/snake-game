package com.game.snake;

import com.almasb.fxgl.dsl.components.AutoRotationComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;

import java.util.List;

import static com.almasb.fxgl.core.math.FXGLMath.random;
import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static com.game.snake.EntityTypes.*;

public class SnakeGameFactory implements EntityFactory {

    private static final List<String> foodList = List.of("food1.png", "food2.png", "food3.png", "food4.png", "food5.png", "food6.png", "food6.png", "food7.png", "food8.png", "food9.png", "food10.png", "food11.png", "food12.png", "food13.png");

    @Spawns("snakehead")
    public Entity snakeHead(SpawnData mahiInit) {
        return entityBuilder(mahiInit).type(SNAKEHEAD)
                                      .bbox(new HitBox(BoundingShape.box(20, 25)))
                                      .with(new SnakeComponent(), new CollidableComponent(true))
                                      .with(new AutoRotationComponent())
                                      .build();
    }

    @Spawns("snake")
    public Entity snakeBody(SpawnData mahiInit) {
        var view = texture("snakebody.png");
        return entityBuilder(mahiInit).type(SNAKE)
                                      .bbox(new HitBox(BoundingShape.box(20, 25)))
                                      .collidable()
                                      .view(view)
                                      .build();
    }

    @Spawns("food")
    public Entity snakeFood(SpawnData mahiInit) {
        return entityBuilder(mahiInit).type(FOOD)
                                      .viewWithBBox(texture(random(foodList).get(), 32, 32))
                                      .with(new FoodComponent(), new CollidableComponent(true))
                                      .build();
    }

}
