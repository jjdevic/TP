package TP1.Pr3.control.commands;

import TP1.Pr3.logic.Game;

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
	public Command parse(String[] commandWords) {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
            return this;
        }

        return null;
	}
}