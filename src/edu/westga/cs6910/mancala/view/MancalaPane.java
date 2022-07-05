package edu.westga.cs6910.mancala.view;

import edu.westga.cs6910.mancala.model.Game;
import edu.westga.cs6910.mancala.model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Defines a GUI for the Mancala game.
 * This class was started by CS6910
 * 
 * @author Alyssa Harris
 * @version 7-2-22
 */
public class MancalaPane extends BorderPane {
	private Game theGame;
	private GridPane pnContent;
	private HumanPane pnHumanPlayer;
	private ComputerPane pnComputerPlayer;
	private StatusPane pnStatus;
	private NewGamePane pnChooseFirstPlayer;
	private MancalaHelpDialog helpDialog;
	private MancalaMenuBar menuBar;
	
	/**
	 * Creates a pane object to provide the view for the specified
	 * Game model object.
	 * 
	 * @param 	theGame		the domain model object representing the Mancala game
	 * 
	 * @requires theGame != null
	 * @ensures	 the pane is displayed properly
	 */
	public MancalaPane(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;

		this.helpDialog = new MancalaHelpDialog();
		this.helpDialog.showHelpDialog();
		
		this.pnContent = new GridPane();
		
		this.createMenu();
		
		this.addFirstPlayerChooserPane(theGame);
			
		this.addComputerPlayerPane(theGame);
		
		this.addHumanPlayerPane(theGame);

		this.addGameStatusPane(theGame);
		
		this.setCenter(this.pnContent);
	}

	private void addGameStatusPane(Game theGame) {
		this.pnStatus = new StatusPane(theGame);
		HBox centerBox = new HBox();
		centerBox.getStyleClass().add("pane-border");		
		centerBox.getChildren().add(this.pnStatus);
		this.pnContent.add(centerBox, 0, 3);
	}

	private void addHumanPlayerPane(Game theGame) {
		this.pnHumanPlayer = new HumanPane(theGame);
		this.pnHumanPlayer.setDisable(true);		
		HBox leftBox = new HBox();
		leftBox.getStyleClass().add("pane-border");	
		leftBox.getChildren().add(this.pnHumanPlayer);
		this.pnContent.add(leftBox, 0, 2);
	}

	private void addComputerPlayerPane(Game theGame) {
		this.pnComputerPlayer = new ComputerPane(theGame);
		this.pnComputerPlayer.setDisable(true);		
		HBox rightBox = new HBox();
		rightBox.getStyleClass().add("pane-border");	
		rightBox.getChildren().add(this.pnComputerPlayer);
		this.pnContent.add(rightBox, 0, 1);
	}
	
	private void createMenu() {
		VBox vbxMenuHolder = new VBox();
		
		this.menuBar = new MancalaMenuBar(this.theGame);
		Menu mnuFile = this.menuBar.createFileMenu();
		Menu mnuSettings = this.menuBar.createStrategyMenu();
		Menu mnuDialog = this.menuBar.createDialogMenu();

		MenuBar mnuMain = new MenuBar();				
		mnuMain.getMenus().addAll(mnuFile, mnuSettings, mnuDialog);
		
		vbxMenuHolder.getChildren().add(mnuMain);
		vbxMenuHolder.prefWidthProperty().bind(this.widthProperty());
		this.setTop(vbxMenuHolder);
	}
		
	private void addFirstPlayerChooserPane(Game theGame) {
		HBox topBox = new HBox();
		topBox.getStyleClass().add("pane-border");	
		this.pnChooseFirstPlayer = new NewGamePane(theGame);
		topBox.getChildren().add(this.pnChooseFirstPlayer);
		this.pnContent.add(topBox, 0, 0);
	}
	
	/**
	 * Defines the panel in which the user selects which Player plays first.
	 */
	private final class NewGamePane extends GridPane {
		private RadioButton radHumanPlayer;
		private RadioButton radComputerPlayer;
		private RadioButton radRandomPlayer;
		private ComboBox<Integer> cmbGoalScore;
		
		private Game theGame;
		private Player theHuman;
		private Player theComputer;

		private NewGamePane(Game theGame) {
			this.theGame = theGame;
			
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
					MancalaPane.this.theGame.setStartingStones(startingStones);
				}
			});
			this.add(this.cmbGoalScore, 1, 0);
		}

		public boolean isHumanFirst() {
			return this.radHumanPlayer.isSelected();
		}
		
		public boolean isComputerFirst() {
			return this.radComputerPlayer.isSelected();
		}
		
		public boolean isRandomFirst() {
			return this.radRandomPlayer.isSelected();
		}

		public void chooseRandomPlayer() {
			if (Math.random() * 10 <= 4) {
				MancalaPane.this.theGame.startNewGame(MancalaPane.this.theGame.getHumanPlayer());
			} else {
				MancalaPane.this.theGame.startNewGame(MancalaPane.this.theGame.getComputerPlayer());
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
				MancalaPane.this.pnChooseFirstPlayer.setDisable(true);

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
				MancalaPane.this.pnComputerPlayer.setDisable(false);
				MancalaPane.this.pnChooseFirstPlayer.setDisable(true);
				MancalaPane.this.theGame.startNewGame(NewGamePane.this.theComputer);
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
				MancalaPane.this.pnChooseFirstPlayer.setDisable(true);
				MancalaPane.this.pnHumanPlayer.setDisable(false);
				MancalaPane.this.theGame.startNewGame(NewGamePane.this.theHuman);
			}
		}
	}
}
