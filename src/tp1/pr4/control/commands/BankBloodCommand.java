package tp1.pr4.control.commands;

import tp1.pr4.exceptions.CommandExecuteException;
import tp1.pr4.exceptions.NotEnoughCoinsException;
import tp1.pr4.exceptions.UnvalidPositionException;
import tp1.pr4.logic.Game;

public class BankBloodCommand extends Command{
	private int x;
	private int y;
	private int z;

	static String helpMessage = "add a blood bank with cost z in position x, y";
	public BankBloodCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public BankBloodCommand() {
		super("bank","b","[b]ank <x> <y> <z>",helpMessage);
	}
	
	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		try {
			game.addBank(x, y, z);
			game.update();
			return true;
		}
		catch(NotEnoughCoinsException | UnvalidPositionException ex) {
			System.out.println(ex.getMessage());
			throw new CommandExecuteException(String.format("[ERROR]: Failed to add bak", ex));
		}
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
