package edu.westga.cs6910.mancala.view;

import edu.westga.cs6910.mancala.model.Game;
import edu.westga.cs6910.mancala.model.HumanPlayer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Defines the panel that displays the setup of this player's side of the board.
 * It also allows the user to select the Pit Number to take the stones on their
 * turn.
 * 
 */
public class HumanPane extends GridPane implements InvalidationListener {
	private ComboBox<String> cmbPitChoice;
	private Button btnTakeTurn;

	private HumanPlayer theHuman;
	private Game theGame;

	/**
	 * Creates a new HumanPane
	 * 
	 * @param theGame the model object from which this panel gets its data
	 * 
	 * @requires theGame != null
	 */
	public HumanPane(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid Game object");
		}

		this.theGame = theGame;
		this.theGame.addListener(this);
		this.theHuman = this.theGame.getHumanPlayer();

		this.buildPane();
	}

	private void buildPane() {
		HBox topBox = new HBox();
		topBox.getChildren().add(new Label("Human"));
		this.add(topBox, 0, 0);

		this.setHgap(50);

		this.createUserInteractionArea();

		int column = 0;
		for (column = 0; column < this.theGame.getBoardSize() / 2 - 1; column++) {
			this.add(new PitPane(column, false, this.theGame), column + 1, 1);
		}

		this.add(new PitPane(column, true, this.theGame), column + 1, 1);
	}

	private void createUserInteractionArea() {
		GridPane interactionPane = new GridPane();
		interactionPane.getStyleClass().add("pane-border");
		interactionPane.getStyleClass().add("bg-highlight-style");

		this.cmbPitChoice = new ComboBox<String>();
		this.cmbPitChoice.getItems().add("Please select");

		for (int count = 0; count < HumanPane.this.theGame.getBoardSize() / 2 - 1; count++) {
			this.cmbPitChoice.getItems().add("Pit " + count);
		}
		this.cmbPitChoice.getSelectionModel().select("Please select");
		interactionPane.add(this.cmbPitChoice, 0, 0);

		this.btnTakeTurn = new Button("Take Turn");
		this.btnTakeTurn.setOnAction(new TakeTurnListener());
		interactionPane.add(this.btnTakeTurn, 0, 1);

		this.add(interactionPane, 0, 1);
	}

	@Override
	public void invalidated(Observable arg0) {
		if (this.theGame.getIsGameOver()) {
			this.setDisable(true);
			return;
		}

		boolean myTurn = this.theGame.getCurrentPlayer() == this.theHuman;
		this.setDisable(!myTurn);
	}

	private class TakeTurnListener implements EventHandler<ActionEvent> {
		/*
		 * Tells the Game to have its current player (i.e., the human Player) take its
		 * turn.
		 * 
		 */
		@Override
		public void handle(ActionEvent event) {
			int pitChoice = this.getSelectedPitNumber();

			if (!HumanPane.this.theGame.getIsGameOver() && pitChoice != -1) {
				HumanPane.this.theGame.play(pitChoice);
			}

		}

		/**
		 * Gets the pit selected by the user. If the selection is invalid, this method
		 * will display an Alert describing what happened
		 * 
		 * @return the selected pit number, if valid -1, otherwise
		 */
		private int getSelectedPitNumber() {
			Object objChoice = HumanPane.this.cmbPitChoice.getValue();
			if (objChoice == null || objChoice.toString().equals("Please select")) {
				Alert altSelectPit = new Alert(AlertType.ERROR);
				altSelectPit.setTitle("Mancala");
				altSelectPit.setHeaderText("You must select a pit first");
				altSelectPit.showAndWait();
				return -1;
			}
			String strChoice = objChoice.toString();
			int pitNumber = Integer.parseInt(strChoice.replaceAll("Pit ", "").trim());
			if (HumanPane.this.theGame.getStones(pitNumber) <= 0) {
				Alert altNoStones = new Alert(AlertType.ERROR);
				altNoStones.setTitle("Mancala");
				altNoStones.setHeaderText("This pit does not contain any stone");
				altNoStones.showAndWait();
				return -1;
			} else {
				return pitNumber;
			}
		}
	}
}
