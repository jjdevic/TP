package tp1.pr4.control.commands;

import tp1.pr4.exceptions.CommandParseException;
import tp1.pr4.logic.Game;

public class ExitCommand extends Command{
	
	static String helpMessage = "exit game";
	public ExitCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public ExitCommand() {
		super("exit","e","[e]xit",helpMessage);
	}
	
	@Override
	public boolean execute(Game game) {
		game.exit();
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
			if (commandWords.length > 1) {
				throw new CommandParseException("[ERROR]: Command update: " + incorrectNumberOfArgsMsg);
			}
            return this;
        }

        return null;
	}
}