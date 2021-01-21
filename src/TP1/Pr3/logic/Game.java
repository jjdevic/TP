package TP1.Pr3.logic;

import java.util.Random;

import TP1.Pr3.logic.gameObjects.GameObjectBoard;
import TP1.Pr3.logic.gameObjects.IAttack;
import TP1.Pr3.view.GamePrinter;
import TP1.Pr3.view.IPrintable;

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

	public boolean addSlayer(int x, int y) {
		if (checkIfPossitionIsAble(x, y)) {					//Coordenas X, Y cambiadas
			if(player.enoghCoins(Slayer.cost)) {
				board.add(new Slayer(this, y, x));
				player.buy(Slayer.cost);
				return true;
			}
			else errorMessageCoins();
		}
		else errorMessagePossition();
		return false;
	}
	
	private void addVampires() {
		int y = getDimensionX() - 1, x;
		
		if(Vampire.vampireRemaining > 0 && rand.nextDouble() < level.getFrequency()) {
			x = rand.nextInt(getDimensionY());
			if(board.isInPosition(x, y) == null) board.add(new Vampire(this, x, y));
		}
		
		if(Vampire.vampireRemaining > 0 && !Dracula.isDracula && rand.nextDouble() < level.getFrequency()) {
			x = rand.nextInt(getDimensionY());
			if(board.isInPosition(x, y) == null) board.add(new Dracula(this, x, y));
		}
		
		if(Vampire.vampireRemaining > 0 && rand.nextDouble() < level.getFrequency()) {
			x = rand.nextInt(getDimensionY());
			if(board.isInPosition(x, y) == null) board.add(new ExplosiveVampire(this, x, y));
		}
	}
	
	public boolean addVampiresCheat(String type, int x, int y) {
		if (Vampire.vampireRemaining > 0) {
			if(checkIfPossitionIsAble(x, y)) {
				if(type.equals("d")) {
					if(!Dracula.isDracula) {
						board.add(new Dracula(this, y, x)); 
						return true;
					}
					else {
						System.out.println("[ERROR]: Dracula is alive");
						return false;
					}
				}
				else if(type.equals("e")) {
					board.add(new ExplosiveVampire(this, y, x));
					return true;
				}
				else board.add(new Vampire(this, y, x)); return true;
			}
			else errorMessagePossition();
		}
		else System.out.println("[ERROR]: No more remaining vampires left");
		return false;
	}
	
	public boolean addBank(int x, int y, int z) {
		if(checkIfPossitionIsAble(x, y)) {
			if (player.enoghCoins(z)) {
				board.add(new BankBlood(this, y, x, z));
				player.buy(z);
				return true;
			}
			else errorMessageCoins();
		}
		else errorMessagePossition();
		return false;
	}

	public boolean garlicPush() {
		if(player.enoghCoins(GarlicPushCost)) {
			board.garlicPush();
			player.buy(GarlicPushCost);
			return true;
		}
		else errorMessageCoins();
		return false;
	}

	public boolean lightFlash() {
		if(player.enoghCoins(LightFlashCost)) {
			board.lightFlash();
			player.buy(LightFlashCost);
			return true;
		}
		else errorMessageCoins();
		return false;
	}
	
	public boolean isFinished() {
		String game_over = "[Game over] ";
		if(Vampire.vampireHasArrived) {
			System.out.println(this);
			System.out.println(game_over + "Vampires win!");
			return true;
		}
		else if(Vampire.vampireDead == level.getNumberOfVampires()) {
			System.out.println(this);
			System.out.println(game_over + "Slayers win!");
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
		return board.isInPosition(x, y);
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

	public void superCoins() {
		player.setCoins(SuperCoins);
	}
	
	private void givePlayerCoins() {
		if(rand.nextFloat() > 0.5) player.giveCoins();									//Monedas que recibe el player por turno
	}
	
	public void givePlayerBankCoins(int coins) {
		player.setCoins(coins);
	}
	
	private boolean checkIfPossitionIsAble(int x, int y) {
		return board.isInPosition(y, x) == null && x < getDimensionX() - 1 && y < getDimensionY() && y >= 0 && x >= 0;
	}
	
	public boolean checkIfPossitionIsEmpty(int x, int y) {
		return board.isInPosition(x, y) == null;
	}
	
	private void errorMessageCoins() {
		System.out.println("[ERROR]: Not enough coins");
	}
	
	private void errorMessagePossition() {
		System.out.println("[ERROR]: Invalid position");
	}
	
	private boolean lastCycleCount() {
		return Vampire.vampireHasArrived || Vampire.vampireDead == level.getNumberOfVampires();
	}
	
}
