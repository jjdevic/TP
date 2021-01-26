package tp1.pr2.logic;

import java.util.Random;

import tp1.pr2.logic.gameObjects.GameObjectBoard;
import tp1.pr2.logic.gameObjects.IAttack;
import tp1.pr2.view.GamePrinter;
import tp1.pr2.view.IPrintable;

public class Game implements IPrintable{
	private Level level;
	private Long seed;
	private Random rand;
	private GameObjectBoard board;
	private GamePrinter printer;
	private Player player;
	
	private boolean playerExit;
	private int cycle;
	
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
	}
	
	public void exit() {
		playerExit = true;
	}

	public void update() {
		givePlayerCoins();
		board.move();
		board.attack();
		addVampire();
		board.remove();
		cycle++;
	}		
	
	public String toString() {
		return printer.toString();
	}

	public boolean addSlayer(int x, int y) {
		if (x < getDimensionX() - 1 && x >= 0 && y < getDimensionY() && y >= 0 && board.isInPosition(y, x) == null) {		//Coordenas X, Y cambiadas
			if(player.enoghCoins()) {
				board.add(new Slayer(this, y, x));
				player.buySlayer();
				return true;
			}
			else System.out.println("[Error] Not enough coins");
		}
		else System.out.println("[Error] Invalid position");
		return false;
	}
	
	public void addVampire() {
		if(rand.nextDouble() <= level.getFrequency() && Vampire.vampireRemaining > 0) {
			board.add(new Vampire(this, rand.nextInt(getDimensionY()), getDimensionX() - 1));
		}
	}
	
	private void givePlayerCoins() {
		player.setCoins(rand.nextFloat() >= 0.5);
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
		return "Number of cycles: " + cycle + "\n" +
				"Coins: " + Player.coins + "\n" +
				"Remaining vampires: " + Vampire.vampireRemaining + "\n" +
				"Vampires on the board: " + Vampire.vampireOnBoard + "\n";
	}
	
}
