package tp1.prFinal.logic;

public class Player {
	public static int coins;
	
	public Player() {
		coins = 50;						//Monedas iniciales
	}
	
	public void setCoins(int coins) {
		Player.coins += coins;				
	}

	public boolean enoghCoins(int cost) {
		return coins >= cost;
	}

	public void buy(int cost) {
		coins -= cost;
	}

	public void giveCoins() {
		coins += 10;
	}
}
