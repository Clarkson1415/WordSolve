package com.example.wordsolve;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class ParticleEffects
{
    public static void ShowDustEffect(Tile tile)
    {
        var x = tile.getBoundsInLocal().getCenterX();
        var y = tile.getBoundsInLocal().getCenterY();

        System.out.printf("x, y = %f, %f", x, y);
        Random rand = new Random();

        for (int i = 0; i < 50; i++)
        {
            var randomOffsetX = rand.nextDouble(-1, 1) * tile.getTileSize();
            var randomOffsetY = rand.nextDouble(-1, 1) * tile.getTileSize();

            System.out.printf("random offset x = %f", randomOffsetX);
            System.out.printf("random offset y = %f", randomOffsetY);

            double puffCentreX = x + randomOffsetX;
            double puffCentreY = y + randomOffsetY;

            Circle c = new Circle(puffCentreX, puffCentreY, 5, Color.SANDYBROWN);
            tile.getChildren().addFirst(c);

            // Animate outward a little
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.6), c);
            tt.setByX(rand.nextDouble(-1, 1) * tile.getTileSize() / 2);
            tt.setByY(rand.nextDouble(-1, 1) * tile.getTileSize() / 2);

            ScaleTransition st = new ScaleTransition(Duration.seconds(0.6), c);
            st.setFromX(1.0);
            st.setToX(2.0);
            st.setFromY(1.0);
            st.setToY(2.0);

            FadeTransition ft = new FadeTransition(Duration.seconds(0.6), c);
            ft.setFromValue(0.8);
            ft.setToValue(0.0);

            ParallelTransition pt = new ParallelTransition(c, tt, st, ft);
            pt.setOnFinished(e -> tile.getChildren().remove(c));
            pt.play();
        }
    }
}
