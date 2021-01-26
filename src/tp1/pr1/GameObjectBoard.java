package tp1.pr1;

import java.util.Random;

import tp1.pr1.logic.*;

public class GameObjectBoard {
	private VampireList vampireList;
	private SlayerList slayerList;
	private Game game;
	private Random rand;
	private Level level;
	
	public GameObjectBoard(Game game, Level level, Random rand) {
		this.game = game;
		this.level = level;
		initBoard();
		this.rand = rand;
	}
	
	private void initBoard() {
		vampireList = new VampireList(this.game);
		slayerList = new SlayerList(this.game);		
	}
	
	public void update() {
		move();
		attack();
		addVampires();
		remove();
	}
	
	public String getPositionToString(int x, int y) {
		if(vampireList.isInPosition(x, y)) {
			return vampireList.inPosition(x, y).toString();		
		}
		
		if(slayerList.isInPosition(x, y)) {
			return slayerList.inPosition(x, y).toString();		
		}
		
		return " ";
	}
	
	public boolean addSlayer(int x, int y) {
		if(!vampireList.isInPosition(x, y) && game.playerCoins() >= 50 && y < game.getDimensionX() - 1) {				//50 es el coste de un Slayer
			slayerList.addSlayer(x, y);
			game.playerBuySlayer();
			return true;
		}
		return false;
	}
	
	private void addVampires() {
		if(rand.nextDouble() <= level.getFrequency()) {
			vampireList.addVampire(rand.nextInt(level.getY()), level.getNumberOfVampires());
		}
	}
	
	private void move() {
		vampireList.vampiresMove();
	}
	
	private void attack() {
		vampireList.vampireAttack();
		slayerList.slayerAttack();
	}
	
	private void remove() {
		removeSlayers();
		removeVampires();
	}
	
	public boolean isSlayerInPosition(int x, int y) {
		if(slayerList.getNSlayers() > 0) {
			if(slayerList.isInPosition(x, y + 1)) {
				slayerList.inPosition(x, y + 1).reciveAttack(1);
			}
		}
		return slayerList.isInPosition(x, y + 1);
	}
	
	public boolean isVampireInPosition(int x, int y) {
		if(vampireList.getNVampires() > 0) {
			if(vampireList.isInPosition(x, y)) {
				vampireList.inPosition(x, y).reciveAttack(1);
			}
		}
		
		return slayerList.isInPosition(x, y);
	}
	
	private void removeVampires() {
		vampireList.removeVampire();
	}
	
	private void removeSlayers() {
		slayerList.removeSlayer();
	}
	
	public int remainingVampires() {
		return level.getNumberOfVampires() - vampireList.getNVampiresAux();
	}
	
	public int nVampires() {
		return vampireList.getNVampires();
	}
	
	public boolean slayerWins() {
		return remainingVampires() + nVampires() == 0;
	}
	
	public boolean vampireWins() {
		return vampireList.vampireWins(level.getX());
	}
	
}
