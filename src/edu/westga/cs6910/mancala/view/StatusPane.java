package edu.westga.cs6910.mancala.view;

import edu.westga.cs6910.mancala.model.Game;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Defines the pane that displays the total number of stones in
 * each store.  It also displays the winner (or tie) at the end
 * of the game
 * This class was started by CS6910
 * 
 * @author Alyssa Harris
 * @version 7-2-22
 */
public class StatusPane extends GridPane implements InvalidationListener {
	private Game theGame;
	private Label lblStatus;
	
	/**
	 * Creates a new status pane that observes the specified game. 
	 * 
	 * @param theGame	the model object from which this pane gets its data
	 * 
	 * @requires 	theGame != null
	 */
	public StatusPane(Game theGame) {
		this.theGame = theGame;
		
		this.theGame.addListener(this);
		
		this.buildPane();
	}

	private void buildPane() {
		HBox topBox = new HBox();
		topBox.getStyleClass().add("box-center");	
		topBox.getStyleClass().add("box-padding");
		topBox.getChildren().add(new Label("~~ Game Info ~~"));
		this.add(topBox, 0, 0);
		
		HBox middleBox = new HBox();
		middleBox.getStyleClass().add("box-padding");
		this.lblStatus = new Label(this.theGame.toString());
		middleBox.getChildren().add(this.lblStatus);
		this.add(middleBox, 0, 1);
	}

	@Override
	public void invalidated(Observable observable) {
		this.lblStatus.setText(this.theGame.toString());
	}
}
