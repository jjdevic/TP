package tp1.pr3.control.commands;

import tp1.pr3.logic.Game;

public class AddSlayerCommand extends Command{
	private int x;
	private int y;
	
	static String helpMessage = "add a slayer in position x, y";
	public AddSlayerCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		// TODO Auto-generated constructor stub
	}
	
	public AddSlayerCommand() {
		super("add","a","[a]dd <x> <y>",helpMessage);
	}

	@Override
	public boolean execute(Game game) {
		if(game.addSlayer(x, y)) {
			game.update();
			return true;
		}
		return false;
	}

	@Override
	public Command parse(String[] commandWords) {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0]) && commandWords.length == 3) {
			try {
				x = Integer.valueOf(commandWords[1]);
				y = Integer.valueOf(commandWords[2]);
			} 
			catch(NumberFormatException nfe) {
				return null;
			}
            return this;
        }
        return null;
	}

}
