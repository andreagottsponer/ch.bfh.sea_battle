package ch.bfh.sea_battle.utilities;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class NavigationBar extends HBox {
    private Button leftButton = null;
    private Button rightButton = null;
    private Label titleLabel = null;

    //lazy instantiation
    public Button getLeftButton() {
        if (this.leftButton == null) {
            this.leftButton = new Button();
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.getChildren().add(this.leftButton);
            this.getChildren().add(hBox);
        }
        return this.leftButton;
    }

    //lazy instantiation
    public Button getRightButton() {
        if (this.rightButton == null) {
            this.rightButton = new Button();
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.getChildren().add(this.rightButton);
            this.getChildren().add(hBox);
        }
        return this.rightButton;
    }

    //lazy instantiation
    public Label getTitleLabel() {
        if (this.titleLabel == null) {
            this.titleLabel = new Label();
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().add(this.titleLabel);
            HBox.setHgrow(hBox, Priority.ALWAYS);
            this.getChildren().add(hBox);
        }
        return this.titleLabel;
    }

    public NavigationBar() {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10));
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
