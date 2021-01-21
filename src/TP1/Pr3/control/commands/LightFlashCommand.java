package TP1.Pr3.control.commands;

import TP1.Pr3.logic.Game;

public class LightFlashCommand extends Command{
	
	static String helpMessage = "kills all the vampires";
	public LightFlashCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	public LightFlashCommand() {
		super("light","l","[l]ight", helpMessage);
	}
	
	@Override
	public boolean execute(Game game) {
		if(game.lightFlash()) {
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
