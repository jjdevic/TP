package TP1.Pr4.logic;

public class Player {
	public static int coins;
	
	public Player() {
		coins = 50;						//Monedas iniciales
	}
	
	public void setCoins(int coins) {
		this.coins += coins;				
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
