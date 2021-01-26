package tp1.pr3.control.commands;

import tp1.pr3.logic.Game;

public class GarlicPushCommand extends Command{

	static String helpMessage = "pushes back vampires";
	public GarlicPushCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public GarlicPushCommand() {
		super("garlic","g","[g]arlic", helpMessage);
	}
	
	@Override
	public boolean execute(Game game) {
		if (game.garlicPush()) {
			game.update();
			return true;
		}
		return false;
	}

	@Override
	public Command parse(String[] commandWords) {
		if(name.equals(commandWords[0]) || shortcut.equals(commandWords[0]) && commandWords.length == 1) {
            return this;
        }

        return null;
	}

}
