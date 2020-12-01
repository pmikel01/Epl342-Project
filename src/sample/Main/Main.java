package sample.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.MainScenesControllers.SignInController;
import sample.Main.DBUtil;
import sample.Main.InsertDB;

import java.sql.*;

public class Main extends Application {
    private double x, y;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../MainScenes/sign_in.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        Connection conn = DBUtil.getDBConnection();
        //ADD DATA TO DB
//        InsertDB.insertData(conn);

        SignInController controller = fxmlLoader.getController();
        //create query
        controller.initData(conn);

        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        //we gonna drag the frame
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
