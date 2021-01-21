package TP1.Pr2.control.commands;

import TP1.Pr2.logic.Game;

public class AddCommand extends Command{
	private int x;
	private int y;
	
	static final String helpMessage = "add a slayer in position x, y";
	public AddCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		// TODO Auto-generated constructor stub
	}
	
	public AddCommand() {
		super("add","a","[a]dd <x> <y>",helpMessage);
	}

	@Override
	public boolean execute(Game game) {
		if(game.addSlayer(x, y)) {
			game.update();
			return true;
		}
		else return false;
	}

	@Override
	public Command parse(String[] commandWords) {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0])) {
			x = Integer.parseInt(commandWords[1]);
			y = Integer.parseInt(commandWords[2]);
            return this;
        }
        return null;
	}

}
