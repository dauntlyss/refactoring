package edu.westga.cs6910.mancala.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * Defines the panel that gives the instructions at the 
 * start of each game
 *
 * @author Alyssa Harris
 * @version Jul 2, 2022
 */
public class MancalaHelpDialog {
	private Alert message;
	private boolean shouldShowHelpDialog;
	
	/**
	 * Creates a new pop-up message box at the start of the game
	 */
	public MancalaHelpDialog() {
		this.message = new Alert(AlertType.CONFIRMATION);
		this.shouldShowHelpDialog = true;
	}
	
	/***
	 * Builds and shows the help dialog
	 * 
	 * @return shouldShowHelpDialog
	 */
	public boolean showHelpDialog() {
		if (!this.shouldShowHelpDialog) {
			return false;
		}

		this.message.setTitle("CS6910 - Better Mancala");
		
		String helpMessage = "Mancala rules:\nPlay against the computer.\n"
				+ "Alternate taking turns, selecting a pit with stones.\n" 
				+ "The stones are taken from this pit and placed, one at a\n"
				+ "   time into consecutive pits in counter-clockwise fashion.\n" 
				+ "The game ends when one player no longer has any stones\n"
				+ "   to distribute.\n"
				+ "The goal is to get more stones into your store,\n" 
				+ "than your opponent has in their store.";
				
		this.message.setHeaderText(helpMessage);
		this.message.setContentText("Would you like to see this dialog at the start of the next game?");
		
		ButtonType btnYes = new ButtonType("Yes");
		ButtonType btnNo = new ButtonType("No");
		this.message.getButtonTypes().setAll(btnYes, btnNo);
		
		Optional<ButtonType> result = this.message.showAndWait();
		
		return result.get() == btnYes; 
	}
}
