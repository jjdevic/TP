package tp1.pr4.logic;

import tp1.pr4.logic.gameObjects.IAttack;

public class ExplosiveVampire extends Vampire{

	public ExplosiveVampire(Game game, int x, int y) {
		super(game, x, y);
		symbol = "EV";
	}
	
	@Override
	public void reduceAmount() {
		super.reduceAmount();
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
