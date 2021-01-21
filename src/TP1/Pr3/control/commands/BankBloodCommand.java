package TP1.Pr3.control.commands;

import TP1.Pr3.logic.Game;

public class BankBloodCommand extends Command{
	private int x;
	private int y;
	private int z;

	static String helpMessage = "add a blood bank with cost z in position x, y.";
	public BankBloodCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public BankBloodCommand() {
		super("bank","b","[b]ank <x> <y> <z>",helpMessage);
	}
	
	@Override
	public boolean execute(Game game) {
		if (game.addBank(x, y, z)) {
			game.update();
			return true;
		}
		return false;
	}

	@Override
	public Command parse(String[] commandWords) {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0]) && commandWords.length == 4) {
			x = Integer.parseInt(commandWords[1]);
			y = Integer.parseInt(commandWords[2]);
			z = Integer.parseInt(commandWords[3]);
            return this;
        }

        return null;
	}

}
