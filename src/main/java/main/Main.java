package main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainView;

public class Main extends Application {

	public static Scene scene;

	public static void main(String[] a) throws ParserConfigurationException, SAXException, IOException {
		launch(a);

	}

	@Override
	public void start(Stage stage) throws Exception {

		scene = new Scene(FluentViewLoader.fxmlView(MainView.class).load().getView());

		stage.setScene(scene);
		stage.show();

	}

}
