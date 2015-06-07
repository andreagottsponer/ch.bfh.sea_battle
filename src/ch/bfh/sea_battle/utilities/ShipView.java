package ch.bfh.sea_battle.utilities;

import ch.bfh.sea_battle.model.ConfigurationManager;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;

public class ShipView extends ImageView {

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private int direction;
    private int length;

    private int width = ConfigurationManager.sharedInstance().getGridWidth();
    private int height = ConfigurationManager.sharedInstance().getGridHeight();
    private int cellSize = ConfigurationManager.sharedInstance().getCellSize();
    private int margin = ConfigurationManager.sharedInstance().getCellMargin();

    public ShipView(boolean interactionEnabled, int length) {

        this.length = length;
        this.direction = 0;

        if (interactionEnabled) {
            this.setCursor(Cursor.HAND);

            this.setOnMousePressed(e -> {
                if (e.getClickCount() == 1) {
                    orgSceneX = e.getSceneX();
                    orgSceneY = e.getSceneY();
                    orgTranslateX = this.getTranslateX();
                    orgTranslateY = this.getTranslateY();
                } else if (e.getClickCount() == 2) {
                    this.setRotate(this.getRotate() + 90);
                    this.direction = (int)((this.getRotate() % 360) / 90);

                    double offsetX = e.getSceneX() - orgSceneX;
                    double offsetY = e.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;
                    this.setTranslateX(newTranslateX);
                    this.setTranslateY(newTranslateY);

                    if (this.length % 2 == 0) {
                        this.setLayoutX(getLayoutX() + 20);
                        this.setLayoutY(getLayoutY() + 20);
                    }
                }
            });

            this.setOnMouseDragged(e -> {
                if (e.getSceneX() > this.margin && e.getSceneX() < (2 * this.margin + this.width * this.cellSize) && e.getSceneY() > 46 && e.getSceneY() < (46 + this.margin + this.height * this.cellSize)) {
                    double offsetX = e.getSceneX() - orgSceneX;
                    double offsetY = e.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;
                    this.setTranslateX(newTranslateX);
                    this.setTranslateY(newTranslateY);
                }
            });

            this.setOnMouseReleased(e -> {
                int cellSize = ConfigurationManager.sharedInstance().getCellSize();
                double offsetX = e.getSceneX() - orgSceneX;
                double offsetY = e.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;

                if ((newTranslateX % cellSize) > 0) {
                    if ((newTranslateX % cellSize) > (cellSize / 2)) {
                        this.setTranslateX(newTranslateX + (cellSize - (newTranslateX % cellSize)));
                    } else {
                        this.setTranslateX(newTranslateX - (newTranslateX % cellSize));
                    }
                } else {
                    if (Math.abs(newTranslateX % cellSize) > (cellSize / 2)) {
                        this.setTranslateX(newTranslateX - (cellSize - Math.abs(newTranslateX % cellSize)));
                    } else {
                        this.setTranslateX(newTranslateX + (Math.abs(newTranslateX % cellSize)));
                    }
                }

                if ((newTranslateY % cellSize) > 0) {
                    if ((newTranslateY % cellSize) > (cellSize / 2)) {
                        this.setTranslateY(newTranslateY + (cellSize - (newTranslateY % cellSize)));
                    } else {
                        this.setTranslateY(newTranslateY - (newTranslateY % cellSize));
                    }
                } else {
                    if (Math.abs(newTranslateY % cellSize) > (cellSize / 2)) {
                        this.setTranslateY(newTranslateY - (cellSize - Math.abs(newTranslateY % cellSize)));
                    } else {
                        this.setTranslateY(newTranslateY + (Math.abs(newTranslateY % cellSize)));
                    }
                }
            });
        }
    }

    public int getDirection() {
        return this.direction;
    }

    public int getLength() {
        return this.length;
    }
}