package tp1.pr4.control.commands;

import tp1.pr4.exceptions.*;
import tp1.pr4.logic.Game;

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
	public boolean execute(Game game) throws CommandExecuteException{
		try {
			return game.addVampiresCheat(type, x, y);

		}
		catch(DraculaIsAliveException | NoMoreVampiresException | UnvalidPositionException ex) {
			System.out.println(ex.getMessage());
			throw new CommandExecuteException(String.format("[ERROR]: Failed to add this vampire", ex));
		}
		
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException{
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
					if(!type.equals("d") && !type.equals("e")) throw new CommandParseException("[ERROR]: Unvalid argument: [v]ampire [<type>] <x> <y>. Type = {\"\"|\"D\"|\"E\"}");
				}
				catch(NumberFormatException nfe2) {
					throw new CommandParseException("[ERROR]: Unvalid argument for add vampire command, number expected: [v]ampire [<type>] <x> <y>. Type = {\"\"|\"D\"|\"E\"}");
				}
			}
            return this;
        }
        return null;
	}
}
