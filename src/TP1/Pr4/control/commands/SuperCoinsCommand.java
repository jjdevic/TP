package TP1.Pr4.control.commands;

import TP1.Pr4.exceptions.CommandParseException;
import TP1.Pr4.logic.Game;

public class SuperCoinsCommand extends Command{
	
	static String helpMessage = "add 1000 coins";
	public SuperCoinsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public SuperCoinsCommand() {
		super("coins","c","[c]oins",helpMessage);
	}
	
	@Override
	public boolean execute(Game game) {
		game.superCoins();
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
			if (commandWords.length != 1) {
				throw new CommandParseException("[ERROR]: Command coins: " + incorrectNumberOfArgsMsg);
			}
            return this;
        }
        return null;
	}
}
