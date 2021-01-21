package TP1.Pr2.control;

import java.util.Scanner;
import TP1.Pr2.logic.Game;
import TP1.Pr2.view.GamePrinter;
import TP1.Pr2.control.commands.*;

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
   