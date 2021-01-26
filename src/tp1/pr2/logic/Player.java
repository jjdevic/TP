package tp1.pr2.logic;

public class Player {
	public static int coins;
	
	public Player() {
		coins = 50;						//Monedas iniciales
	}
	
	public void setCoins(boolean isMoreCoins) {
		if(isMoreCoins) coins += 10;				//Añade 10 coins con una probabilidad del 50%
	}

	public boolean enoghCoins() {
		return coins >= 50;
	}
	
	public void buySlayer() {
		coins = coins - Slayer.cost;
	}
}
