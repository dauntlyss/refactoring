package edu.westga.cs6910.mancala.controllers;

import edu.westga.cs6910.mancala.model.Game;
import edu.westga.cs6910.mancala.view.MancalaPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Driver for the application. Creates the GUI and shows it.
 * 
 * @author 	CS6910
 * @version Summer 2022
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("CS6910 - Simple Mancala");
		primaryStage.setResizable(false);
		Game theGame = new Game();

		MancalaPane root = new MancalaPane(theGame);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Entry point into the application
	 * 
	 * @param args
	 *            Not used
	 */
	public static void main(String[] args) {
		launch(args);
	}
}