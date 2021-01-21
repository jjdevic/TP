package TP1.Pr1;

public class Player {
	int coins;
	
	public Player() {
		coins = 50;						//Monedas iniciales
	}
	
	public void setCoins(boolean isMoreCoins) {
		if(isMoreCoins) coins += 10;				//Añade 10 coins con una probabilidad del 50%
	}

	public boolean enoghCoins() {
		return coins >= 50;
	}
	
	public int getCoins() {
		return coins;
	}
	
	public void buySlayer() {
		coins = coins - 50;
	}
}
