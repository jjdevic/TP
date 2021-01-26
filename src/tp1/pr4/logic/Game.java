package tp1.pr4.logic;

import java.util.Random;

import tp1.pr4.vampiros;
import tp1.pr4.exceptions.*;
import tp1.pr4.logic.gameObjects.GameObjectBoard;
import tp1.pr4.logic.gameObjects.IAttack;
import tp1.pr4.view.GamePrinter;
import tp1.pr4.view.IPrintable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Game implements IPrintable{
	private Level level;
	private Long seed;
	private Random rand;
	private GameObjectBoard board;
	private GamePrinter printer;
	private Player player;
	private boolean playerExit;
	private int cycle;
	
	private static final int LightFlashCost = 50;
	private static final int GarlicPushCost = 10;
	private static final int SuperCoins = 1000;
	
	public Game(Long seed, Level level) {
		this.seed = seed;
		this.level = level;
		initGame();
		playerExit = false;
	}
	
	public void initGame() {
		cycle = 0;
		rand = new Random(seed);
		player = new Player();
		board = new GameObjectBoard();
		printer = new GamePrinter(this, getDimensionX(), getDimensionY());
		Vampire.vampireRemaining = level.getNumberOfVampires();
	}
	
	public void reset() {
		initGame();
		Dracula.isDracula = false;
		Vampire.vampireOnBoard = 0;
	}
	
	public void exit() {
		playerExit = true;
	}

	public void update() {
		givePlayerCoins();
		board.move();
		board.attack();
		addVampires();
		board.remove();
		if(!lastCycleCount()) cycle++;
	}		
	
	public String toString() {
		return printer.toString();
	}

	public boolean addSlayer(int x, int y) throws CommandExecuteException {
		if(checkIfPossitionIsAble(x, y)) {	
			if(player.enoghCoins(Slayer.cost)) {
				board.add(new Slayer(this, y, x));
				player.buy(Slayer.cost);
				return true;
			}
			else throw new NotEnoughCoinsException("[ERROR]: Defender cost is " + Slayer.cost + ": Not enough coins");  
		}
		else throw new UnvalidPositionException("[ERROR]: Position (" + x + ", 	" + y + "): Unvalid position");
	}
	
	private void addVampires() {
		int y = getDimensionX() - 1, x;
		
		if(Vampire.vampireRemaining > 0 && rand.nextDouble() < level.getFrequency()) {
			x = rand.nextInt(getDimensionY());
			if(board.isObjectInPosition(x, y)) board.add(new Vampire(this, x, y));
		}
		
		if(Vampire.vampireRemaining > 0 && !Dracula.isDracula && rand.nextDouble() < level.getFrequency()) {
			x = rand.nextInt(getDimensionY());
			if(board.isObjectInPosition(x, y)) board.add(new Dracula(this, x, y));
		}
		
		if(Vampire.vampireRemaining > 0 && rand.nextDouble() < level.getFrequency()) {
			x = rand.nextInt(getDimensionY());
			if(board.isObjectInPosition(x, y)) board.add(new ExplosiveVampire(this, x, y));
		}
	}
	
	public boolean addVampiresCheat(String type, int x, int y) throws CommandExecuteException {
		if (Vampire.vampireRemaining > 0) {
			if(checkIfPossitionIsEmpty(y, x) && x < getDimensionX() && y < getDimensionY()) {
				if(type.equals("d")) {
					if(!Dracula.isDracula) {
						board.add(new Dracula(this, y, x)); 
						return true;
					}
					else {
						throw new DraculaIsAliveException("[ERROR]: Dracula is already on board");
					}
				}
				else if(type.equals("e")) {
					board.add(new ExplosiveVampire(this, y, x));
					return true;
				}
				else board.add(new Vampire(this, y, x)); return true;
			}
			else throw new UnvalidPositionException("[ERROR]: Position (" + x + ", " + y + "): Unvalid position");
		}
		else throw new NoMoreVampiresException(("[ERROR]: No more remaining vampires left"));
	}
	
	public void addBank(int x, int y, int z) throws CommandExecuteException{
		if(checkIfPossitionIsAble(x, y)) {
			if (player.enoghCoins(z)) {
				board.add(new BankBlood(this, y, x, z));
				player.buy(z);
			}
			else throw new NotEnoughCoinsException("[ERROR]: Defender cost is " + z + ": Not enough coins");
		}
		else throw new UnvalidPositionException("[ERROR]: Position (" + x + "," + y + "): Unvalid position");
	}

	public void garlicPush() throws NotEnoughCoinsException{
		if(player.enoghCoins(GarlicPushCost)) {
			board.garlicPush();
			player.buy(GarlicPushCost);
		}
		else throw new NotEnoughCoinsException("[ERROR]: Garlic Push cost is " + GarlicPushCost + ": Not enough coins");
	}

	public void lightFlash() throws NotEnoughCoinsException{
		if(player.enoghCoins(LightFlashCost)) {
			board.lightFlash();
			player.buy(LightFlashCost);
		}
		else throw new NotEnoughCoinsException("[ERROR]: Light Flash cost is " + LightFlashCost + ": Not enough coins");

	}
	
	public void superCoins() {
		player.setCoins(SuperCoins);
	}
	
	public boolean isFinished() {
		String game_over = "[GAME OVER] ";
		if(Vampire.vampireHasArrived) {
			System.out.println(this);
			System.out.println(game_over + "Vampires win!");
			return true;
		}
		else if(Vampire.vampireDead == level.getNumberOfVampires()) {
			System.out.println(this);
			System.out.println(game_over + "Player wins");
			return true;
		}
		else if (playerExit) {
			System.out.println(game_over + "Nobody wins...");
			return true;
		}
		return false;
	}
	
	public int getDimensionX() {
		return level.getX();
	}
	
	public int getDimensionY() {
		return level.getY();
	}
	
	public IAttack getAttackbleInPosition(int x, int y) {
		return board.getAttacableInPosition(x, y);
	}

	@Override
	public String getPositionToString(int x, int y) {
		return board.toString(x, y);
	}

	@Override
	public String getInfo() {
		String dracula;
		if(Dracula.isDracula) dracula = "Dracula is alive\n";
		else dracula = "";
		return "Number of cycles: " + cycle + "\n" +
				"Coins: " + Player.coins + "\n" +
				"Remaining vampires: " + Vampire.vampireRemaining + "\n" +
				"Vampires on the board: " + Vampire.vampireOnBoard + "\n" + dracula;
	}
	
	private void givePlayerCoins() {
		if(rand.nextFloat() > 0.5) player.giveCoins();									//Monedas que recibe el player por turno
	}
	
	public void givePlayerBankCoins(int coins) {
		player.setCoins(coins);
	}
	
	private boolean checkIfPossitionIsAble(int x, int y) {
		return board.isObjectInPosition(y, x) && x < getDimensionX() - 1 && y < getDimensionY() && y >= 0 && x >= 0;
	}
	
	public boolean checkIfPossitionIsEmpty(int x, int y) {
		return board.isObjectInPosition(x, y);
	}
	
	private boolean lastCycleCount() {
		return Vampire.vampireHasArrived || Vampire.vampireDead == level.getNumberOfVampires();
	}

	public String serialize() {
		return "Cycles: " + cycle + "\n" +
				"Coins: " + Player.coins + "\n" +
				"Level: " + level.getName().toUpperCase() + "\n" +
				"Remaining Vampires: " + Vampire.vampireRemaining + "\n" +
				"Vampires on Board: " + Vampire.vampireOnBoard + "\n\n" +
				"Game Object List: \n" + 
				board.serialize();
		
	}

	public void save(String file) throws IOException {
		file = file + ".dat";
        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter(file))) {
        	myWriter.write(vampiros.welcomeMsg + "\n");
            myWriter.write(serialize());
            System.out.println("Game successfully saved to file " + file + ".");
        }
        catch (IOException e){
            System.out.println("An error ocurred.");
        }
    }
	
}
