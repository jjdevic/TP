package tp1.pr2.control.commands;

import tp1.pr2.logic.Game;

public class HelpCommand extends Command{
	static final String helpMessage = "Prints this help message.";
	public HelpCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public HelpCommand() {
		super("help","h","[h]elp",helpMessage);
	}

	@Override
	public boolean execute(Game game) {
		System.out.println(CommandGenerator.commandHelp());
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
