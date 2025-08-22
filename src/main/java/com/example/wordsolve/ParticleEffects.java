package com.example.wordsolve;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class ParticleEffects
{
    public static void ShowDustEffect(Tile tile)
    {
        Random rand = new Random();

        var width = tile.getTileSize();
        var height = tile.getTileSize();

        for (int i = 0; i < 50; i++) {
            double x, y;

            int edge = rand.nextInt(4); // 0=top,1=right,2=bottom,3=left
            switch (edge) {
                case 0: // top
                    x = rand.nextDouble() * width;
                    y = 0;
                    break;
                case 1: // right
                    x = width;
                    y = rand.nextDouble() * height;
                    break;
                case 2: // bottom
                    x = rand.nextDouble() * width;
                    y = height;
                    break;
                case 3: // left
                    x = 0;
                    y = rand.nextDouble() * height;
                    break;
                default:
                    x = width / 2;
                    y = height / 2;
            }

            Circle c = new Circle(x, y, 5, Color.SANDYBROWN);
            tile.getChildren().addFirst(c);

            var animateTime = 0.5;

            // animate puff outward
            TranslateTransition tt = new TranslateTransition(Duration.seconds(animateTime), c);
            tt.setByX(rand.nextDouble(-1, 1) * width);
            tt.setByY(rand.nextDouble(-1, 1) * height);

            FadeTransition ft = new FadeTransition(Duration.seconds(animateTime), c);
            ft.setFromValue(0.8);
            ft.setToValue(0);

            ScaleTransition st = new ScaleTransition(Duration.seconds(animateTime), c);
            st.setFromX(2.0);
            st.setToX(1.0);
            st.setFromY(2.0);
            st.setToY(1.0);

            ParallelTransition pt = new ParallelTransition(c, tt, st, ft);
            pt.setOnFinished(e -> tile.getChildren().remove(c));
            pt.play();
        }
    }
}
