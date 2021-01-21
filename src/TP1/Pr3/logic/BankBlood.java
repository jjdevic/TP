package TP1.Pr3.logic;

public class BankBlood extends Slayer{
	private int saves;

	public BankBlood(Game game, int x, int y, int saves) {
		super(game, x, y);
		this.saves = saves;
		life = 1;					//LA VIDA INICIAL ES 1
	}
	
	public String toString() {
		return "B [" + saves + "]";
	}
	
	public void attack() {
		game.givePlayerBankCoins(saves / 10);
	}

}
