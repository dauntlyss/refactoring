package edu.westga.cs6910.mancala.view;

import edu.westga.cs6910.mancala.model.Game;
import edu.westga.cs6910.mancala.model.strategies.FarStrategy;
import edu.westga.cs6910.mancala.model.strategies.NearStrategy;
import edu.westga.cs6910.mancala.model.strategies.RandomStrategy;
import edu.westga.cs6910.mancala.model.strategies.SelectStrategy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * Defines all contents needed to display and interact with
 * the menu bar on the mancala game
 *
 * @author Alyssa Harris
 * @version Jul 5, 2022
 */
public class MancalaMenuBar {
	private Menu mnuFile;
	private Menu mnuSettings;
	private Menu mnuDialog;
	private Game theGame;
	private MancalaHelpDialog helpDialog;
	private NewGamePane newGamePane;
	private MancalaPane theMancalaPane;
	
	/**
	 * Creates a new MancalaMenuBar that holds all menus needed for
	 * game.
	 * 
	 * @param theMancalaPane pane object to provide the view for the specified Game model object.
	 * @param theGame the model object from which this bar gets its data
	 */
	public MancalaMenuBar(MancalaPane theMancalaPane, Game theGame) {
		this.theMancalaPane = theMancalaPane;
		this.theGame = theGame;
		this.newGamePane = new NewGamePane(this.theMancalaPane, this.theGame);
		this.helpDialog = new MancalaHelpDialog();
	}

	/**
	 * Creates the strategy menu for the computer player
	 *  
	 * @return mnuSettings the strategy menu
	 */
	public Menu createStrategyMenu() {
		this.mnuSettings = new Menu("_Computer Player");
		this.mnuSettings.setMnemonicParsing(true);
		
		RadioMenuItem mnuNear = this.addStrategyItem("N_ear", "E", new NearStrategy());
		RadioMenuItem mnuFar = this.addStrategyItem("F_ar", "A", new FarStrategy());
		RadioMenuItem mnuRandom = this.addStrategyItem("_Random", "R", new RandomStrategy());
		this.setMenuToggleGroup(mnuNear, mnuFar, mnuRandom);
		
		SelectStrategy currentStrategy = this.theGame.getComputerPlayer().getStrategy();			
		if (currentStrategy.getClass() == NearStrategy.class) {
			mnuNear.setSelected(true);
		} else if (currentStrategy.getClass() == RandomStrategy.class) {
			mnuRandom.setSelected(true);
		} else {
			mnuFar.setSelected(true);
		}

		this.mnuSettings.getItems().addAll(mnuNear, mnuFar, mnuRandom);
		return this.mnuSettings;
	}
	
	private RadioMenuItem addStrategyItem(String radioMenuItemText, String shortcutKey, SelectStrategy someStrategy) {
		RadioMenuItem mnuItem = new RadioMenuItem(radioMenuItemText);
		mnuItem.setAccelerator(KeyCombination.keyCombination("shortcut + " + shortcutKey));
		mnuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MancalaMenuBar.this.theGame.getComputerPlayer().setStrategy(someStrategy);
			}
		});
		mnuItem.setMnemonicParsing(true);
		
		return mnuItem;
	}
	
	private ToggleGroup setMenuToggleGroup(RadioMenuItem item1, RadioMenuItem item2, RadioMenuItem item3) {
		ToggleGroup tglStrategy = new ToggleGroup();
		item1.setToggleGroup(tglStrategy);
		item2.setToggleGroup(tglStrategy);
		item3.setToggleGroup(tglStrategy);
		
		return tglStrategy;
	}
	
	/**
	 * Creates the help dialog menu
	 * 
	 * @return mnuDialog the dialog menu
	 */
	public Menu createDialogMenu() {
		this.mnuDialog = new Menu("_Help");
		this.mnuDialog.setMnemonicParsing(true);
		
		MenuItem mnuContents = new MenuItem("C_ontents");
		mnuContents.setMnemonicParsing(true);
		mnuContents.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
		mnuContents.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MancalaMenuBar.this.helpDialog.showHelpDialog();
			}
		});
		
		MenuItem mnuAbout = new MenuItem("A_bout");
		mnuAbout.setMnemonicParsing(true);
		mnuAbout.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.SHORTCUT_DOWN));
		mnuAbout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert about = new Alert(AlertType.INFORMATION);
				about.setTitle("About");
				String aboutMessage = "Created: July 2, 2022 \nCreator: Alyssa Harris";
				about.setHeaderText(aboutMessage);
				about.showAndWait();
			}
		});
		this.mnuDialog.getItems().addAll(mnuContents, mnuAbout);
		return this.mnuDialog;
	}

	/**
	 * Creates the file menu to start a new game or quit
	 * 
	 * @return mnuFile the file menu
	 */
	public Menu createFileMenu() {
		this.mnuFile = new Menu("_Game");
		this.mnuFile.setMnemonicParsing(true);
	
		MenuItem mnuNew = new MenuItem("_New");
		mnuNew.setMnemonicParsing(true);
		mnuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
		mnuNew.setOnAction(new NewGameListener());
		
		MenuItem mnuExit = new MenuItem("E_xit");
		mnuExit.setMnemonicParsing(true);
		mnuExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
		mnuExit.setOnAction(event -> System.exit(0));
		
		this.mnuFile.getItems().addAll(mnuNew, mnuExit);
		return this.mnuFile;
	}
	
	/** 
	 * Defines the listener for new Game button.
	 */		
	private class NewGameListener implements EventHandler<ActionEvent> {
		@Override
		/** 
		 * Enables the PlayerPanel for the first player and starts a new game. 
		 * Event handler for a click in the New Game button in the menu.
		 */
		public void handle(ActionEvent arg0) {
			if (MancalaMenuBar.this.theMancalaPane.getPnChooseFirstPlayer().isHumanFirst()) {
				MancalaMenuBar.this.theGame.startNewGame(MancalaMenuBar.this.theGame.getHumanPlayer());
			} else if (MancalaMenuBar.this.theMancalaPane.getPnChooseFirstPlayer().isComputerFirst()) {
				MancalaMenuBar.this.theGame.startNewGame(MancalaMenuBar.this.theGame.getComputerPlayer());
			} else if (MancalaMenuBar.this.theMancalaPane.getPnChooseFirstPlayer().isRandomFirst()) {
				MancalaMenuBar.this.newGamePane.chooseRandomPlayer();
			}
		}
	}
}
