package ca.centennial.comp228;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1e1e1e;");

        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/logo.png")));
        logo.setFitWidth(56);
        logo.setFitHeight(56);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);

        Label title = new Label("Job Application Form");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        Label subtitle = new Label("Complete the form and press Submit to save your application.");
        subtitle.setStyle("-fx-font-size: 12px; -fx-text-fill: #cccccc;");

        VBox heading = new VBox(2, title, subtitle);
        HBox header = new HBox(12, logo, heading);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #252526; -fx-padding: 12 18 12 18;");
        root.setTop(header);

        ScrollPane scrollPane = new ScrollPane(new RenderForm());
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #1e1e1e; -fx-background-color: #1e1e1e;");
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 680, 720);

        stage.setTitle("Job Application Form");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
