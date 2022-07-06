package edu.westga.cs6910.mancala.view;

import edu.westga.cs6910.mancala.model.Game;
import edu.westga.cs6910.mancala.model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

/**
 * Defines the NewGamePane for the mancala game
 *
 * @author Alyssa Harris
 * @version Jul 5, 2022
 */
public final class NewGamePane extends GridPane {
		private RadioButton radHumanPlayer;
		private RadioButton radComputerPlayer;
		private RadioButton radRandomPlayer;
		private ComboBox<Integer> cmbGoalScore;
		
		private MancalaPane theMancalaPane;
		private Game theGame;
		private Player theHuman;
		private Player theComputer;

		/**
		 * Creates a pane object to provide the view for the specified game object
		 * 
		 * @param theMancalaPane the pane 
		 * @param theGame the domain model object representing the Mancala game
		 */
		public NewGamePane(MancalaPane theMancalaPane, Game theGame) {
			this.theGame = theGame;
			this.theMancalaPane = theMancalaPane;
			
			this.theHuman = this.theGame.getHumanPlayer();
			this.theComputer = this.theGame.getComputerPlayer();
			
			this.buildPane();
		}
		
		private void buildPane() {
			this.setHgap(20);
			
			this.createNumberOfStonesChoice();
			
			this.createFirstPlayerItems();	
		}

		private void createFirstPlayerItems() {
			this.radHumanPlayer = new RadioButton(this.theHuman.getName() + " first");	
			this.radHumanPlayer.setOnAction(new HumanFirstListener());
			
			this.radComputerPlayer = new RadioButton(this.theComputer.getName() + " first");
			this.radComputerPlayer.setOnAction(new ComputerFirstListener());
			
			this.radRandomPlayer = new RadioButton("Random player first");
			this.radRandomPlayer.setOnAction(new RandomFirstListener());
			
			ToggleGroup group = new ToggleGroup();
			this.radHumanPlayer.setToggleGroup(group);
			this.radComputerPlayer.setToggleGroup(group);
			this.radRandomPlayer.setToggleGroup(group);

			this.add(this.radHumanPlayer, 2, 0);
			this.add(this.radComputerPlayer, 3, 0);
			this.add(this.radRandomPlayer, 4, 0);
		}
		
		private void createNumberOfStonesChoice() {
			Label lblGoalScore = new Label("Starting Stones: ");
			this.add(lblGoalScore, 0, 0);
			
			this.cmbGoalScore = new ComboBox<Integer>();
			this.cmbGoalScore.getItems().addAll(1, 2, 3);
			this.cmbGoalScore.setValue(1);
			this.cmbGoalScore.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					int startingStones = NewGamePane.this.cmbGoalScore.getValue();
					NewGamePane.this.theGame.setStartingStones(startingStones);
				}
			});
			this.add(this.cmbGoalScore, 1, 0);
		}
		
		/**
		 * Determines if the human first button is selected
		 * 
		 * @return true if the humanPlayer button is selected
		 */
		public boolean isHumanFirst() {
			return this.radHumanPlayer.isSelected();
		}
		
		/**
		 * Determines if the computer first button is selected
		 * 
		 * @return true if the computerPlayer button is selected
		 */
		public boolean isComputerFirst() {
			return this.radComputerPlayer.isSelected();
		}
		
		/**
		 * Determines if the randomFirstPlayer button is selected
		 * 
		 * @return true if the randomFirstPlayer button is selected
		 */
		public boolean isRandomFirst() {
			return this.radRandomPlayer.isSelected();
		}
		
		/**
		 * Selects random player to go first
		 */
		public void chooseRandomPlayer() {
			if (Math.random() * 10 <= 4) {
				NewGamePane.this.theGame.startNewGame(NewGamePane.this.theGame.getHumanPlayer());
			} else {
				NewGamePane.this.theGame.startNewGame(NewGamePane.this.theGame.getComputerPlayer());
			}
		}
		
		/** 
		 * Defines the listener for computer player first button.
		 */		
		private class RandomFirstListener implements EventHandler<ActionEvent> {
			@Override
			/** 
			 * Enables the ComputerPlayerPanel and starts a new game. 
			 * Event handler for a click in the computerPlayerButton.
			 */
			public void handle(ActionEvent arg0) {
				NewGamePane.this.theMancalaPane.getPnChooseFirstPlayer().setDisable(true);

				NewGamePane.this.chooseRandomPlayer();
			}
		}
		
		/** 
		 * Defines the listener for computer player first button.
		 */		
		private class ComputerFirstListener implements EventHandler<ActionEvent> {
			@Override
			/** 
			 * Enables the ComputerPlayerPanel and starts a new game. 
			 * Event handler for a click in the computerPlayerButton.
			 */
			public void handle(ActionEvent arg0) {
				NewGamePane.this.theMancalaPane.getPnComputerPlayer().setDisable(false);
				NewGamePane.this.theMancalaPane.getPnChooseFirstPlayer().setDisable(true);
				NewGamePane.this.theGame.startNewGame(NewGamePane.this.theComputer);
			}
		}
	
		/** 
		 * Defines the listener for human player first button.
		 */	
		private class HumanFirstListener implements EventHandler<ActionEvent> {
			@Override
			/** 
			 * Sets up user interface and starts a new game. 
			 * Event handler for a click in the human player button.
			 */
			public void handle(ActionEvent event) {
				NewGamePane.this.theMancalaPane.getPnChooseFirstPlayer().setDisable(true);
				NewGamePane.this.theMancalaPane.getPnHumanPlayer().setDisable(false);
				NewGamePane.this.theGame.startNewGame(NewGamePane.this.theHuman);
			}
		}
}
