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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Defines all contents needed to display and interact with
 * the menu bar on the mancala game
 *
 * @author Alyssa Harris
 * @version Jul 5, 2022
 */
public class MancalaMenuBar extends GridPane {
	private MenuBar mnuMain;
	private Menu mnuFile;
	private Menu mnuSettings;
	private Menu mnuDialog;
	private Game theGame;
	private MancalaHelpDialog helpDialog;
	private MancalaPane theMancalaPane;
	
	/**
	 * Creates a new MancalaMenuBar that holds all menus needed for
	 * game.
	 * 
	 * @param theGame the model object from which this bar gets its data
	 * @param theMancalaPane the pane the menu is added to
	 */
	public MancalaMenuBar(MancalaPane theMancalaPane, Game theGame) {
		this.theGame = theGame;
		
		this.theMancalaPane = theMancalaPane;
		
		this.createMenuBar();
		System.out.println("getting to menu bar");
		
//		this.mnuMain.prefWidthProperty().bind(this.theMancalaPane.widthProperty());
	}
	
	private MenuBar createMenuBar() {
		this.mnuMain = new MenuBar();
		
		this.mnuFile = this.createFileMenu();
		
		this.mnuSettings = this.createStrategyMenu();
		
		this.mnuDialog = this.createDialogMenu();
				
		this.mnuMain.getMenus().addAll(this.mnuFile, this.mnuSettings, this.mnuDialog);
		System.out.println("getting to create menu bar");
		return this.mnuMain;
		
	}

	private Menu createStrategyMenu() {
		this.mnuSettings = new Menu("_Computer Player");
		this.mnuSettings.setMnemonicParsing(true);
		
//		ToggleGroup tglStrategy = new ToggleGroup();
//		
//		RadioMenuItem mnuNear = new RadioMenuItem("N_ear");
//		mnuNear.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN));
//		mnuNear.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent arg0) {
//				MancalaPane.theGame.getComputerPlayer().setStrategy(new NearStrategy());
//			}
//		});
//		mnuNear.setMnemonicParsing(true);
//		mnuNear.setToggleGroup(tglStrategy);
//		
//		RadioMenuItem mnuFar = new RadioMenuItem("F_ar");
//		mnuFar.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));
//		mnuFar.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent arg0) {
//				MancalaPane.this.theGame.getComputerPlayer().setStrategy(new FarStrategy());
//			}
//		});
//		mnuFar.setMnemonicParsing(true);
//		mnuFar.setToggleGroup(tglStrategy);
		
//		RadioMenuItem mnuRandom = new RadioMenuItem("_Random");
//		mnuRandom.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN));
//		mnuRandom.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent arg0) {
//				MancalaPane.this.theGame.getComputerPlayer().setStrategy(new RandomStrategy());
//			}
//		});
//		mnuRandom.setMnemonicParsing(true);
//		mnuRandom.setToggleGroup(tglStrategy);
		RadioMenuItem mnuNear = this.addStrategyItem("N_ear", "E", new NearStrategy());
		RadioMenuItem mnuFar = this.addStrategyItem("F_ar", "A", new FarStrategy());
		RadioMenuItem mnuRandom = this.addStrategyItem("_Random", "R", new RandomStrategy());
		
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
		ToggleGroup tglStrategy = new ToggleGroup();
		
		RadioMenuItem mnuItem = new RadioMenuItem(radioMenuItemText);
		mnuItem.setAccelerator(KeyCombination.keyCombination("shortcut + " + shortcutKey));
		mnuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MancalaMenuBar.this.theGame.getComputerPlayer().setStrategy(someStrategy);
			}
		});
		mnuItem.setMnemonicParsing(true);
		mnuItem.setToggleGroup(tglStrategy);
		
		return mnuItem;
	}
	
	private Menu createDialogMenu() {
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

	private Menu createFileMenu() {
		this.mnuFile = new Menu("_Game");
		this.mnuFile.setMnemonicParsing(true);
	
		MenuItem mnuNew = new MenuItem("_New");
		mnuNew.setMnemonicParsing(true);
		mnuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
		mnuNew.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
//				if (MancalaMenuBar.this.theMancalaPane.pnChooseFirstPlayer.isHumanFirst()) {
//					MancalaPane.this.theGame.startNewGame(MancalaPane.this.theGame.getHumanPlayer());
//				} else if (MancalaPane.this.pnChooseFirstPlayer.isComputerFirst()) {
//					MancalaPane.this.theGame.startNewGame(MancalaPane.this.theGame.getComputerPlayer());
//				} else if (MancalaPane.this.pnChooseFirstPlayer.isRandomFirst()) {
//					MancalaPane.this.pnChooseFirstPlayer.chooseRandomPlayer();
//				}
				System.out.println("Fix new game stuff");
			}
		});
		
		MenuItem mnuExit = new MenuItem("E_xit");
		mnuExit.setMnemonicParsing(true);
		mnuExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
		mnuExit.setOnAction(event -> System.exit(0));
		
		this.mnuFile.getItems().addAll(mnuNew, mnuExit);
		return this.mnuFile;
	}
}
