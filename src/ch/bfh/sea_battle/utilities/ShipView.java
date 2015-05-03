package ch.bfh.sea_battle.utilities;

import javafx.scene.Cursor;
import javafx.scene.image.ImageView;

public class ShipView extends ImageView {

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    public ShipView() {
        this.setCursor(Cursor.HAND);

        this.setOnMousePressed(e -> {
            if (e.getClickCount() == 1) {
                orgSceneX = e.getSceneX();
                orgSceneY = e.getSceneY();
                orgTranslateX = this.getTranslateX();
                orgTranslateY = this.getTranslateY();
            } else if (e.getClickCount() == 2) {
                this.setRotate(this.getRotate() + 90);
            }
        });

        this.setOnMouseDragged(e -> {
            double offsetX = e.getSceneX() - orgSceneX;
            double offsetY = e.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
            this.setTranslateX(newTranslateX);
            this.setTranslateY(newTranslateY);
        });

        this.setOnMouseReleased(e -> {
            System.out.println("XCoordinate: " + e.getSceneX());
            System.out.println("YCoordinate: " + e.getSceneY());

            this.setTranslateX((int) (e.getSceneX() / 80) * 80);
            this.setTranslateY((int) (e.getSceneY() / 80) * 80);

            System.out.println("XCoordinate: " + e.getSceneX());
            System.out.println("YCoordinate: " + e.getSceneY());
        });
    }
}
