package tp1.pr4.control.commands;

import tp1.pr4.exceptions.CommandExecuteException;
import tp1.pr4.exceptions.CommandParseException;
import tp1.pr4.exceptions.NotEnoughCoinsException;
import tp1.pr4.logic.Game;

public class GarlicPushCommand extends Command{

	static String helpMessage = "pushes back vampires";
	public GarlicPushCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public GarlicPushCommand() {
		super("garlic","g","[g]arlic", helpMessage);
	}
	
	@Override
	public boolean execute(Game game) throws CommandExecuteException{
		try {
			game.garlicPush();
			game.update();
			return true;
		} 
		catch(NotEnoughCoinsException ex) {
			System.out.println(ex.getMessage());
			throw new CommandExecuteException(String.format("[ERROR]: Failed to garlic push", ex));
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
			if (commandWords.length > 1) {
				throw new CommandParseException("[ERROR]: Command garlic: " + incorrectNumberOfArgsMsg);
			}
            return this;
        }

        return null;
	}

}
