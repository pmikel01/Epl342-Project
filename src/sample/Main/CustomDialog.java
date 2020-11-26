package sample.Main;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;



public class CustomDialog extends Stage {
    private static final Interpolator EXP_IN = new Interpolator() {
        @Override
        protected double curve(double t) {
            return (t == 1.0) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
        }
    };

    private static final Interpolator EXP_OUT = new Interpolator() {
        @Override
        protected double curve(double t) {
            return (t == 0.0) ? 0.0 : Math.pow(2.0, 10 * (t - 1));
        }
    };

    private ScaleTransition scaleLeft = new ScaleTransition();
    private ScaleTransition scaleRight = new ScaleTransition();

    private SequentialTransition animation = new SequentialTransition(scaleLeft, scaleRight);

    public CustomDialog(String header, String content, String choose) {
        Pane root = new Pane();

        scaleLeft.setFromX(0.01);
        scaleLeft.setFromY(0.01);
        scaleLeft.setToY(1.0);
        scaleLeft.setDuration(Duration.seconds(0.15));
        scaleLeft.setInterpolator(EXP_IN);
        scaleLeft.setNode(root);

        scaleRight.setFromX(0.01);
        scaleRight.setToX(1.0);
        scaleRight.setDuration(Duration.seconds(0.15));
        scaleRight.setInterpolator(EXP_OUT);
        scaleRight.setNode(root);

        initStyle(StageStyle.TRANSPARENT);
        initModality(Modality.APPLICATION_MODAL);

        Rectangle background = null;
        if (choose.equals("avg")) {
            background = new Rectangle(283, 150, Color.LIGHTGREY);
        } else if (choose.equals("fr")) {
            background = new Rectangle(400, 150, Color.LIGHTGREY);
        } else if (choose.equals("like")) {
            background = new Rectangle(500, 150, Color.LIGHTGREY);
        }
        background.setStroke(Color.DARKGREY);
        background.setStrokeWidth(1.5);

        Text headerText = new Text(header);
        headerText.setTextAlignment(TextAlignment.CENTER);
        headerText.setFont(Font.font(20));

        Text contentText = new Text(content);
        contentText.setTextAlignment(TextAlignment.CENTER);
        contentText.setFont(Font.font(25));

        VBox box = new VBox(10,
                headerText,
                new Separator(Orientation.HORIZONTAL),
                contentText
        );
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        box.setFillWidth(true);

        Button btn = new Button("OK");
        btn.setTranslateX(background.getWidth() - 50);
        btn.setTranslateY(background.getHeight() - 50);
        btn.setOnAction(e -> closeDialog());

        root.getChildren().addAll(background, box, btn);
        setScene(new Scene(root, null));
    }
    public void openDialog() {
        show();
        animation.play();
    }
    void closeDialog() {
        animation.setOnFinished(e -> close());
        animation.setAutoReverse(true);
        animation.setCycleCount(2);
        animation.playFrom(Duration.seconds(0.35));
    }
}
