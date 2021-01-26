package tp1.pr4.logic;

public class BankBlood extends Slayer{
	private int saves;

	public BankBlood(Game game, int x, int y, int saves) {
		super(game, x, y);
		this.saves = saves;
		life = 1;					//LA VIDA INICIAL ES 1
	}
	
	@Override
	public String toString() {
		return "B [" + saves + "]";
	}
	
	public void move() {
		if(!isDeath()) game.givePlayerBankCoins(saves / 10);
	}
	
	@Override
	public void attack() {}
	
	@Override
	public String serialize() {
		return "B" + ";" + y + ";" + x + ";" + life + ";" + saves + "\n";
	}

}
