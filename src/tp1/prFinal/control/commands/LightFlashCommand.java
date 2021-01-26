package tp1.prFinal.control.commands;

import tp1.prFinal.exceptions.CommandExecuteException;
import tp1.prFinal.exceptions.CommandParseException;
import tp1.prFinal.exceptions.NotEnoughCoinsException;
import tp1.prFinal.logic.Game;

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
		catch(NotEnoughCoinsException e) {
			throw new CommandExecuteException(String.format("%s[ERROR]: Failed to light flash", e.getMessage()));
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
