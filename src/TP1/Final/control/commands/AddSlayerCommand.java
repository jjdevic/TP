package TP1.Final.control.commands;

import TP1.Final.exceptions.CommandExecuteException;
import TP1.Final.exceptions.CommandParseException;
import TP1.Final.exceptions.NotEnoughCoinsException;
import TP1.Final.exceptions.UnvalidPositionException;
import TP1.Final.logic.Game;

public class AddSlayerCommand extends Command{
	private int x;
	private int y;
	
	static String helpMessage = "add a slayer in position x, y";
	public AddSlayerCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public AddSlayerCommand() {
		super("add","a","[a]dd <x> <y>",helpMessage);
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		try {
			game.addSlayer(x, y);
			game.update();
			return true;
		} 
		catch(NotEnoughCoinsException | UnvalidPositionException e) {
			throw new CommandExecuteException(String.format("%s[ERROR]: Failed to add slayer", e.getMessage()));
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
			if (commandWords.length != 3) throw new CommandParseException("[ERROR]: Incorrect number of arguments for add command: [a]dd <x> <y>");
			try {
				x = Integer.valueOf(commandWords[1]);
				y = Integer.valueOf(commandWords[2]);
			} 
			catch(NumberFormatException nfe) {
				throw new CommandParseException("[ERROR]: Unvalid argument for add slayer command, number expected: [a]dd <x> <y>");
			}
            return this;
        }
        return null;
	}

}
