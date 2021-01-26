package tp1.pr4.control.commands;

import tp1.pr4.exceptions.CommandParseException;
import tp1.pr4.logic.Game;

public class ResetCommand extends Command{
	
	static String helpMessage = "reset game";
	public ResetCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public ResetCommand() {
		super("reset","r","[r]eset",helpMessage);
	}
	
	@Override
	public boolean execute(Game game) {
		game.reset();
		System.out.println(game);
		return false;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
			if (commandWords.length > 1) {
				throw new CommandParseException("[ERROR]: Command reset: " + incorrectNumberOfArgsMsg);
			}
            return this;
        }

        return null;
	}
}