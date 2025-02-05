package tp1.prFinal.control.commands;

import tp1.prFinal.exceptions.CommandParseException;
import tp1.prFinal.logic.Game;

public class SerializeCommand extends Command{

	static String helpMessage = "Serializes the board.";
	public SerializeCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public SerializeCommand() {
		super("serialize","z","Seriali[z]e",helpMessage);
	}
	
	@Override
	public boolean execute(Game game) {
		System.out.println(game.serialize());
		return false;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
			if (commandWords.length > 1) {
				throw new CommandParseException("[ERROR]: Command serialize: " + incorrectNumberOfArgsMsg);
			}
            return this;
        }

        return null;
	}

}
