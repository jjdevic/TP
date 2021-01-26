package tp1.pr3.control.commands;

import tp1.pr3.logic.Game;

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
	public Command parse(String[] commandWords) {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
            return this;
        }

        return null;
	}
}