package tp1.pr3.control;

import tp1.pr3.control.commands.Command;
import tp1.pr3.control.commands.CommandGenerator;
import tp1.pr3.logic.Game;

import java.util.Scanner;

public class Controller {

	public final String prompt = "Command > ";
	public static final String unknownCommandMsg = String.format("Unknown command");
	public static final String invalidCommandMsg = String.format("Invalid command");
	public static final String invalidPositionMsg = String.format("Invalid position");

    private Game game;
    private Scanner scanner;
    
    public Controller(Game game, Scanner scanner) {
	    this.game = game;
	    this.scanner = scanner;
    }
    
    public void printGame() {
   	 System.out.println(game);
   }
    
    public void run() {
    	boolean refreshDisplay = true;
    	
    	while (!game.isFinished()) {
    		if (refreshDisplay) printGame();
    		refreshDisplay = false;
    		
    			
    		System.out.print(prompt + "\n");
    		String s = scanner.nextLine();
    		
    		String[] parameters = s.toLowerCase().trim().split(" ");
    		System.out.println("[DEBUG] Executing: " + s);
    		Command command = CommandGenerator.parse(parameters);
    		
    		if (command != null) {
    			refreshDisplay = command.execute(game);
    		}
    		else {
    			System.out.println("[ERROR]: "+ unknownCommandMsg);
    		}
    	}
    }
}    
   