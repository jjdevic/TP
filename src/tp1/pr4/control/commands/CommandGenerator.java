package tp1.pr4.control.commands;

import tp1.pr4.control.Controller;
import tp1.pr4.exceptions.CommandParseException;

public class CommandGenerator {
	private static Command[] availableCommands = {
		new AddSlayerCommand(),
		new HelpCommand(),
		new ResetCommand(),
		new ExitCommand(),
		new UpdateCommand(),
		new GarlicPushCommand(),
		new LightFlashCommand(),
		new BankBloodCommand(),
		new SuperCoinsCommand(),
		new AddVampireCommand(),
		new SaveCommand(),
		new SerializeCommand()
	};
	
	public static Command parse(String[] words) throws CommandParseException  {
        Command command = null;
        for(int i = 0; i < availableCommands.length; i++) {
            command = availableCommands[i].parse(words);
            if(command != null) break;
        }
        if(command == null) throw new CommandParseException("[ERROR]: " + Controller.unknownCommandMsg);
        return command;
    }
	
	public static String commandHelp(){
        String help = "Available commands:\n";

        for(int i = 0; i < availableCommands.length; i++) {
            help += availableCommands[i].helpText();
        }
        return help;
    }
}
