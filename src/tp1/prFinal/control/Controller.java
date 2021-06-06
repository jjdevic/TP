package tp1.prFinal.control;

import tp1.prFinal.control.commands.Command;
import tp1.prFinal.control.commands.CommandGenerator;
import tp1.prFinal.exceptions.GameException;
import tp1.prFinal.logic.Game;

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
    		
    		try {
    			Command command = CommandGenerator.parse(parameters);
    			refreshDisplay = command.execute(game);
    		}
    		catch(GameException e) {
    			System.out.format(e.getMessage() + "%n%n");
    		}
    	}
    }
    
}    
   