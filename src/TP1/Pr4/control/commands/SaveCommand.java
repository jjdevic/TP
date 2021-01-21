package TP1.Pr4.control.commands;

import java.io.IOException;

import TP1.Pr4.exceptions.CommandExecuteException;
import TP1.Pr4.exceptions.CommandParseException;
import TP1.Pr4.logic.Game;

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
		try {
			game.save(file);
		}
		catch (IOException e){
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
