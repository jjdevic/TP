package tp1.pr1.control;

import java.util.Scanner;

import tp1.pr1.logic.Game;
import tp1.pr1.view.GamePrinter;

public class Controller {

	public final String prompt = "Command > ";
	public static final String helpMsg = String.format(
			"Available commands:%n" +
			"[a]dd <x> <y>: add a slayer in position x, y%n" +
			"[h]elp: show this help%n" + 
			"[r]eset: reset game%n" + 
			"[e]xit: exit game%n"+ 
			"[n]one | []: update%n");
	
	public static final String unknownCommandMsg = String.format("Unknown command");
	public static final String invalidCommandMsg = String.format("Invalid command");
	public static final String invalidPositionMsg = String.format("Invalid position");

    private Game game;
    private Scanner scanner;
    private GamePrinter gamePrinter;
    
    public Controller(Game game, Scanner scanner) {
	    this.game = game;
	    this.scanner = scanner;
	    gamePrinter = new GamePrinter(game, game.getDimensionX(), game.getDimensionY());
    }
    
    public void printGame() {
   	 System.out.println(game);
   }
    
    public void run() {
    	System.out.println("\n" + helpMsg);
    	
		while(!game.isFinished()) {
			System.out.println(gamePrinter.toString());
			System.out.print(prompt);
			String input = scanner.nextLine().toLowerCase();
			command(input);
			System.out.println();
			game.update();
		}
    }
    
    public void command(String input) { 
    	String[] sInput = input.split("\\s+");
    	
		switch(sInput[0]) {
		case "a":
		case "add":
			int x = Integer.parseInt(sInput[1]);
			int y = Integer.parseInt(sInput[2]);
			
			if(!game.addSlayer(y, x)) {
				System.err.print("Coordenadas NO validas, vuelva a introducir el comando deseado -> ");
				input = scanner.nextLine().toLowerCase();	
				command(input);
			}
			break;
			
		case "h":
		case "help":
			System.out.println(helpMsg);
			System.out.print(prompt);
			input = scanner.nextLine().toLowerCase();	
			command(input);
			break;
			
		case "r":
		case "reset":
			game.reset();
			break;
			
		case "e":
		case "exit":
			System.out.println("GAME OVER");
			System.exit(0);
			break;
			
		case "":
		case "n":
			break;
			
		default:
			System.err.print("Comando Incorrecto. Vuelva a introducir el comando desado -> ");
			input = scanner.nextLine().toLowerCase();	
			command(input);
			break;
    	}
    }
}

