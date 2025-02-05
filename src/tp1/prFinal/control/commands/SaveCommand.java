package tp1.prFinal.control.commands;

import tp1.prFinal.exceptions.CommandExecuteException;
import tp1.prFinal.exceptions.CommandParseException;
import tp1.prFinal.logic.Game;
import tp1.prFinal.vampiros;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveCommand extends Command {
	private String file;

	static String helpMessage = "Save the state of the game to a file.";
	public SaveCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public SaveCommand() {
		super("save","s","[S]ave <filename>",helpMessage);
	}
	
	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		file = file + ".dat";
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(vampiros.welcomeMsg + "\n");
			writer.write(game.serialize());
	        System.out.println("Game successfully saved to file " + file + ".");
		}
		catch (IOException e){
			System.out.println("An error ocurred.");
			throw new CommandExecuteException(String.format("[ERROR]: Failed to save game", e));
		}
		return false;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
			if (commandWords.length == 1 || commandWords.length > 2) {
				throw new CommandParseException("[ERROR]: Command save: " + incorrectNumberOfArgsMsg);
			}
			file = commandWords[1];
            return this;
        }
        return null;
	}
}
