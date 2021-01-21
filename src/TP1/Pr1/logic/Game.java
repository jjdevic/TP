package TP1.Pr1.logic;

import TP1.Pr1.GameObjectBoard;
import TP1.Pr1.Player;
import java.util.Random;

public class Game {
	private Level level;
	private Long seed;
	private Random rand;
	private GameObjectBoard board;
	private Player player;
		
	private int cycle;
	
	public Game(Long seed, Level level) {
		rand = new Random(seed);
		this.seed = seed;
		this.level = level;
		initGame();
	}
	
	public void initGame() {
		cycle = 0;
		player = new Player();
		board = new GameObjectBoard(this, this.level, this.rand);
	}
	
	public void reset() {
		initGame();
	}

	public void update() {
		givePlayerCoins();
		board.update();
		cycle++;
		displayInfo();
	}
	
	public void displayInfo() {
		System.out.println("Number of cycles: " + cycle);
		System.out.println("Coins: " + player.getCoins());
		System.out.println("Remaining Vampires: " + board.remainingVampires());
		System.out.println("Vampires on the board: " + board.nVampires());
	}
	
	public String getPositionToString(int x, int y) {
		return board.getPositionToString(x, y);
	}
	
	public boolean addSlayer(int x, int y) {
		return board.addSlayer(x, y);
	}
	
	private void givePlayerCoins() {
		if(rand.nextFloat() >= 0.5) player.setCoins(true);
	}
	
	public boolean vampiresWins() {
		return board.vampireWins();
	}
	
	public boolean slayerWins() {
		return board.slayerWins();
	}
	
	public boolean isFinished() {
		if(vampiresWins()) {
			System.out.println("\nVAMPIRES WIN!!!!");
			return true;
		}
		else if(slayerWins()) {
			System.out.println("\nSLAYERS WIN!!!!");
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
	
	public boolean isSlayerInPosition(int x, int y) {
		return board.isSlayerInPosition(x, y);
	}
	
	public boolean isVampireInPosition(int x, int y) {
		return board.isVampireInPosition(x, y);
	}
	
	public int playerCoins() {
		return player.getCoins();
	}
	
	public void playerBuySlayer() {
		player.buySlayer();
	}
}
