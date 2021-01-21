package TP1.Pr4.control.commands;

import TP1.Pr4.exceptions.CommandExecuteException;
import TP1.Pr4.exceptions.CommandParseException;
import TP1.Pr4.exceptions.NotEnoughCoinsException;
import TP1.Pr4.exceptions.UnvalidPositionException;
import TP1.Pr4.logic.Game;

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
		catch(NotEnoughCoinsException | UnvalidPositionException ex) {
			System.out.println(ex.getMessage());
			throw new CommandExecuteException(String.format("[ERROR]: Failed to add slayer", ex));
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
