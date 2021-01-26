package tp1.pr3.control.commands;

import tp1.pr3.logic.Game;

public class AddVampireCommand extends Command{
	private int x;
	private int y;
	private String type;
	
	static String helpMessage = "Type = {\"\"|\"D\"|\"E\"}: add a vampire in position x, y";
	public AddVampireCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public AddVampireCommand() {
		super("vampire","v","[v]ampire [<type>] <x> <y>", helpMessage);
	}
	
	@Override
	public boolean execute(Game game) {
		return game.addVampiresCheat(type, x, y);
	}

	@Override
	public Command parse(String[] commandWords) {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0]) && commandWords.length >= 3) {
			type = commandWords[1].toLowerCase();
			try {
				x = Integer.valueOf(commandWords[1]);
				y = Integer.valueOf(commandWords[2]);
			} 
			catch(NumberFormatException nfe1) {
				try {
					x = Integer.valueOf(commandWords[2]);
					y = Integer.valueOf(commandWords[3]);
				}
				catch(NumberFormatException nfe2) {
					return null;
				}
			}
            return this;
        }
        return null;
	}
}
