package TP1.Pr4.control;

import java.util.Scanner;
import TP1.Pr4.logic.Game;
import TP1.Pr4.control.commands.*;
import TP1.Pr4.exceptions.GameException;

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
   