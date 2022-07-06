package edu.westga.cs6910.mancala.view;

import edu.westga.cs6910.mancala.model.Game;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
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
		
		this.menuBar = new MancalaMenuBar(this, this.theGame);
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
		this.pnChooseFirstPlayer = new NewGamePane(this, theGame);
		topBox.getChildren().add(this.pnChooseFirstPlayer);
		this.pnContent.add(topBox, 0, 0);
	}

	/**
	 * @param pnChooseFirstPlayer the pnChooseFirstPlayer to set
	 */
	public void setPnComputerPlayer(ComputerPane pnComputerPlayer) {
		this.pnComputerPlayer = pnComputerPlayer;
	}
	
	/**
	 * @return the pnComputerPlayer
	 */
	public ComputerPane getPnComputerPlayer() {
		return this.pnComputerPlayer;
	}
//
	/**
	 * @return the pnChooseFirstPlayer
	 */
	public NewGamePane getPnChooseFirstPlayer() {
		return this.pnChooseFirstPlayer;
	}

	/**
	 * @param pnChooseFirstPlayer the pnChooseFirstPlayer to set
	 */
	public void setPnChooseFirstPlayer(NewGamePane pnChooseFirstPlayer) {
		this.pnChooseFirstPlayer = pnChooseFirstPlayer;
	}

	/**
	 * @return the pnHumanPlayer
	 */
	public HumanPane getPnHumanPlayer() {
		return this.pnHumanPlayer;
	}

	/**
	 * @param pnHumanPlayer the pnHumanPlayer to set
	 */
	public void setPnHumanPlayer(HumanPane pnHumanPlayer) {
		this.pnHumanPlayer = pnHumanPlayer;
	}
}
