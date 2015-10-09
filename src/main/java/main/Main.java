package main;

import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import node.FXMLNode;
import node.FXMLReader;
import view.MainView;

public class Main extends Application {

	public static Scene scene;

	public static void main(String[] a) {
		launch(a);

	}

	@Override
	public void start(Stage stage) {

		scene = new Scene(FluentViewLoader.fxmlView(MainView.class).load().getView());

		FXMLNode root = new FXMLNode("src/main/resources/fxmlparser/fxml.fxml");
		root.setChildren(FXMLReader.getChildren(root.getPath()));
		root.getChildren().forEach(r -> {
			System.out.println(r.getName() + "; " + r.getPath());
		});

		stage.setScene(scene);
		stage.show();

	}

}
