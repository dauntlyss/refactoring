package edu.westga.cs6910.mancala.view;

import java.util.Optional;

import edu.westga.cs6910.mancala.model.Game;
import edu.westga.cs6910.mancala.model.Player;
import edu.westga.cs6910.mancala.model.strategies.FarStrategy;
import edu.westga.cs6910.mancala.model.strategies.NearStrategy;
import edu.westga.cs6910.mancala.model.strategies.RandomStrategy;
import edu.westga.cs6910.mancala.model.strategies.SelectStrategy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Defines a GUI for the Mancala game.
 * This class was started by CS6910
 * 
 */
public class MancalaPane extends BorderPane {
	private Game theGame;
	private GridPane pnContent;
	private HumanPane pnHumanPlayer;
	private ComputerPane pnComputerPlayer;
	private StatusPane pnStatus;
	private NewGamePane pnChooseFirstPlayer;
	private boolean shouldShowHelpDialog;
	
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

		this.shouldShowHelpDialog = true;
		this.shouldShowHelpDialog = this.showHelpDialog();
		
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
		
		MenuBar mnuMain = new MenuBar();
		
		Menu mnuFile = this.createFileMenu();
		
		Menu mnuSettings = this.createStrategyMenu();
				
		mnuMain.getMenus().addAll(mnuFile, mnuSettings);
		vbxMenuHolder.getChildren().addAll(mnuMain);
		this.setTop(vbxMenuHolder);
	}

	private Menu createStrategyMenu() {
		Menu mnuSettings = new Menu("_Computer Player");
		mnuSettings.setMnemonicParsing(true);
		
		ToggleGroup tglStrategy = new ToggleGroup();
		
		RadioMenuItem mnuNear = new RadioMenuItem("N_ear");
		mnuNear.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN));
		mnuNear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MancalaPane.this.theGame.getComputerPlayer().setStrategy(new NearStrategy());
			}
		});
		mnuNear.setMnemonicParsing(true);
		mnuNear.setToggleGroup(tglStrategy);
		
		RadioMenuItem mnuFar = new RadioMenuItem("F_ar");
		mnuFar.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));
		mnuFar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MancalaPane.this.theGame.getComputerPlayer().setStrategy(new FarStrategy());
			}
		});
		mnuFar.setMnemonicParsing(true);
		mnuFar.setToggleGroup(tglStrategy);
		
		RadioMenuItem mnuRandom = new RadioMenuItem("_Random");
		mnuRandom.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN));
		mnuRandom.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MancalaPane.this.theGame.getComputerPlayer().setStrategy(new RandomStrategy());
			}
		});
		mnuRandom.setMnemonicParsing(true);
		mnuRandom.setToggleGroup(tglStrategy);
		
		SelectStrategy currentStrategy = this.theGame.getComputerPlayer().getStrategy();			
		if (currentStrategy.getClass() == NearStrategy.class) {
			mnuNear.setSelected(true);
		} else if (currentStrategy.getClass() == RandomStrategy.class) {
			mnuRandom.setSelected(true);
		} else {
			mnuFar.setSelected(true);
		}

		mnuSettings.getItems().addAll(mnuNear, mnuFar, mnuRandom);
		return mnuSettings;
	}

	private Menu createFileMenu() {
		Menu mnuFile = new Menu("_Game");
		mnuFile.setMnemonicParsing(true);
	
		MenuItem mnuNew = new MenuItem("_New");
		mnuNew.setMnemonicParsing(true);
		mnuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
		mnuNew.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (MancalaPane.this.pnChooseFirstPlayer.isHumanFirst()) {
					MancalaPane.this.theGame.startNewGame(MancalaPane.this.theGame.getHumanPlayer());
				} else if (MancalaPane.this.pnChooseFirstPlayer.isComputerFirst()) {
					MancalaPane.this.theGame.startNewGame(MancalaPane.this.theGame.getComputerPlayer());
				} else if (MancalaPane.this.pnChooseFirstPlayer.isRandomFirst()) {
					MancalaPane.this.pnChooseFirstPlayer.chooseRandomPlayer();
				}
			}
		});
		
		MenuItem mnuExit = new MenuItem("E_xit");
		mnuExit.setMnemonicParsing(true);
		mnuExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
		mnuExit.setOnAction(event -> System.exit(0));
		
		mnuFile.getItems().addAll(mnuNew, mnuExit);
		return mnuFile;
	}
	
	private void addFirstPlayerChooserPane(Game theGame) {
		HBox topBox = new HBox();
		topBox.getStyleClass().add("pane-border");	
		this.pnChooseFirstPlayer = new NewGamePane(theGame);
		topBox.getChildren().add(this.pnChooseFirstPlayer);
		this.pnContent.add(topBox, 0, 0);
	}
	
	private boolean showHelpDialog() {
		if (!this.shouldShowHelpDialog) {
			return false;
		}

		Alert message = new Alert(AlertType.CONFIRMATION);
		message.setTitle("CS6910 - Better Mancala");
		
		String helpMessage = "Mancala rules:\nPlay against the computer.\n"
				+ "Alternate taking turns, selecting a pit with stones.\n" 
				+ "The stones are taken from this pit and placed, one at a\n"
				+ "   time into consecutive pits in counter-clockwise fashion.\n" 
				+ "The game ends when one player no longer has any stones\n"
				+ "   to distribute.\n"
				+ "The goal is to get more stones into your store,\n" 
				+ "than your opponent has in their store.";
				
		message.setHeaderText(helpMessage);
		message.setContentText("Would you like to see this dialog at the start of the next game?");
		
		ButtonType btnYes = new ButtonType("Yes");
		ButtonType btnNo = new ButtonType("No");
		message.getButtonTypes().setAll(btnYes, btnNo);
		
		Optional<ButtonType> result = message.showAndWait();
		
		return result.get() == btnYes; 
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
