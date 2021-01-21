package TP1.Pr3.control.commands;

import TP1.Pr3.logic.Game;

public class UpdateCommand extends Command{
	
	static String helpMessage = "update";
	public UpdateCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public UpdateCommand() {
		super("n","","[n]one | []",helpMessage);
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