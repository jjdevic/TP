package tp1.pr3.control.commands;

import tp1.pr3.logic.Game;

public class SuperCoinsCommand extends Command{
	
	static String helpMessage = "add 1000 coins";
	public SuperCoinsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public SuperCoinsCommand() {
		super("coins","c","[c]oins",helpMessage);
	}
	
	@Override
	public boolean execute(Game game) {
		game.superCoins();
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
