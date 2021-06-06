package tp1.prFinal.logic;

import tp1.prFinal.exceptions.*;
import tp1.prFinal.logic.gameObjects.GameObjectBoard;
import tp1.prFinal.logic.gameObjects.IAttack;
import tp1.prFinal.view.GamePrinter;
import tp1.prFinal.view.IPrintable;

import java.util.Random;

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

	public void addSlayer(int x, int y) throws CommandExecuteException {
		if(!checkIfPossitionIsAvailable(x, y)) throw new UnvalidPositionException("[ERROR]: Position (" + x + ", " + y + "): Unvalid position%n");
		if(!player.enoghCoins(Slayer.cost)) throw new NotEnoughCoinsException("[ERROR]: Defender cost is " + Slayer.cost + ": Not enough coins%n"); 
		
		board.add(new Slayer(this, y, x));
		player.buy(Slayer.cost);
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
		if (Vampire.vampireRemaining <= 0) {
			throw new NoMoreVampiresException(("[ERROR]: No more remaining vampires left%n"));
		}
		if(!(checkIfPossitionIsEmpty(y, x) && x < getDimensionX() && y < getDimensionY())) {
			throw new UnvalidPositionException("[ERROR]: Position (" + x + ", " + y + "): Unvalid position%n");
		}
		
		if(type.equals("d")) {
			if(Dracula.isDracula) throw new DraculaIsAliveException("[ERROR]: Dracula is already on board%n");
			board.add(new Dracula(this, y, x)); 
		}
		else if(type.equals("e")) {
			board.add(new ExplosiveVampire(this, y, x));
		}
		else board.add(new Vampire(this, y, x));
		return true;
	}
	
	public void addBank(int x, int y, int z) throws CommandExecuteException{
		if(!checkIfPossitionIsAvailable(x, y)) throw new UnvalidPositionException("[ERROR]: Position (" + x + "," + y + "): Unvalid position%n");
		if (!player.enoghCoins(z)) throw new NotEnoughCoinsException("[ERROR]: Bank cost is " + z + ": Not enough coins%n");
		
		board.add(new BankBlood(this, y, x, z));
		player.buy(z);
	}

	public void garlicPush() throws NotEnoughCoinsException{
		if(!player.enoghCoins(GarlicPushCost)) throw new NotEnoughCoinsException("[ERROR]: Garlic Push cost is " + GarlicPushCost + ": Not enough coins%n");
		
		board.garlicPush();
		player.buy(GarlicPushCost);
	}

	public void lightFlash() throws NotEnoughCoinsException{
		if(!player.enoghCoins(LightFlashCost)) throw new NotEnoughCoinsException("[ERROR]: Light Flash cost is " + LightFlashCost + ": Not enough coins%n");
		
		board.lightFlash();
		player.buy(LightFlashCost);
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
	
	private boolean checkIfPossitionIsAvailable(int x, int y) {
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
	
}
