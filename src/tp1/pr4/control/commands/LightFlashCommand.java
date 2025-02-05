package tp1.pr4.control.commands;

import tp1.pr4.exceptions.CommandExecuteException;
import tp1.pr4.exceptions.CommandParseException;
import tp1.pr4.exceptions.NotEnoughCoinsException;
import tp1.pr4.logic.Game;

public class LightFlashCommand extends Command{
	
	static String helpMessage = "kills all the vampires";
	public LightFlashCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public LightFlashCommand() {
		super("light","l","[l]ight", helpMessage);
	}
	
	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		try {
			game.lightFlash();
			game.update();
			return true;
		}
		catch(NotEnoughCoinsException ex) {
			System.out.println(ex.getMessage());
			throw new CommandExecuteException(String.format("[ERROR]: Failed to light flash", ex));
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException{
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
			if (commandWords.length > 1) {
				throw new CommandParseException("[ERROR]: Command light flash: " + incorrectNumberOfArgsMsg);
			}
            return this;
        }
        return null;
	}
}
