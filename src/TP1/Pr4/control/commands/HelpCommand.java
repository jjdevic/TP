package TP1.Pr4.control.commands;

import TP1.Pr4.exceptions.CommandParseException;
import TP1.Pr4.logic.Game;

public class HelpCommand extends Command{
	
	static String helpMessage = "show this help";
	public HelpCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public HelpCommand() {
		super("help","h","[h]elp",helpMessage);
	}

	@Override
	public boolean execute(Game game) {
		System.out.println(CommandGenerator.commandHelp());
		return false;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException{
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
			if (commandWords.length > 1) {
				throw new CommandParseException("[ERROR]: Command help: " + incorrectNumberOfArgsMsg);
			}
            return this;
        }

        return null;
	}

}
