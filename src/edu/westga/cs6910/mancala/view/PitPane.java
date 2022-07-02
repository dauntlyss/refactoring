package edu.westga.cs6910.mancala.view;

import edu.westga.cs6910.mancala.model.Game;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Defines the panel that displays a pit with its stones
 * 
 * @author Alyssa Harris
 * @version 7-2-22
 */
public class PitPane extends GridPane implements InvalidationListener {
	private Label lblPitNumber;
	private Label lblNumberOfStones;
	
	private int pitNumber;
	private Game theGame;
	
	/**
	 * Sets up the PitPanel for use
	 * 
	 * @param pitNumber	The number identifying this pit
	 * @param isStore	true if this pit is a store; false otherwise
	 * @param theGame	The Game object that this pit is representing
	 */
	public PitPane(int pitNumber, boolean isStore, Game theGame) {
		if (pitNumber < 0) {
			throw new IllegalArgumentException("Pit Number cannot be negative");
		}
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid Game object");
		}
		
		this.pitNumber = pitNumber;
		this.theGame = theGame;
		this.theGame.addListener(this);
		
		this.getStyleClass().add("pane-border");	
		this.setPrefHeight(90);
		this.setPrefWidth(90);
		
		this.buildPanel(isStore);
	}

	private void buildPanel(boolean isStore) {
		this.lblPitNumber = new Label("Pit: " + this.pitNumber);
		this.add(this.lblPitNumber, 0, 0);
		if (isStore) {
			this.getStyleClass().add("bg-gray-style");
		}
			
		int stones = this.theGame.getStones(this.pitNumber);
		this.lblNumberOfStones = new Label("Stones: " + stones);
		this.add(this.lblNumberOfStones, 0, 1);
	}

	@Override
	public void invalidated(Observable theObservable) {
		int newStones = this.theGame.getStones(this.pitNumber);
		this.lblNumberOfStones.setText("Stones: " + newStones);
	}
}
