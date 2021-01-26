package tp1.pr3.logic;

import tp1.pr3.logic.gameObjects.IAttack;

public class ExplosiveVampire extends Vampire{

	public ExplosiveVampire(Game game, int x, int y) {
		super(game, x, y);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "EV [" + life + "]";
	}
	
	@Override
	public void reduceAmount() {
		vampireOnBoard -= 1;
		vampireDead += 1;
		explode();
	}
	
	public void explode() {
		if(y < game.getDimensionX() - 1) {
			for(int i = x - 1; i <= x + 1; i++) {
				for(int j = y - 1; j <= y + 1; j++) {
					IAttack other = game.getAttackbleInPosition(i, j);
					if(other != null) other.receiveSlayerAttack(damage);
				}
			}
		}
	}
	
	
}
