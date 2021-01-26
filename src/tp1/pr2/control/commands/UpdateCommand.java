package tp1.pr2.control.commands;

import tp1.pr2.logic.Game;

public class UpdateCommand extends Command{
	static final String helpMessage = "update.";
	public UpdateCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public UpdateCommand() {
		super("n","","[n]one | []: ",helpMessage);
	}
	
	@Override
	public boolean execute(Game game) {
		game.update();
		return true;
	}

	@Override
	public Command parse(String[] commandWords) {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
            return this;
        }

        return null;
	}
}