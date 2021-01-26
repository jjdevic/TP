package tp1.pr3.control.commands;

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
		new AddVampireCommand()
	};
	
	public static Command parse(String[] words) {
        Command command = null;
        for(int i = 0; i < availableCommands.length; i++){
            command = availableCommands[i].parse(words);
            if(command != null) break;
        }
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
