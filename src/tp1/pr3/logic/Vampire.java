package tp1.pr3.logic;

import tp1.pr3.logic.gameObjects.GameObject;
import tp1.pr3.logic.gameObjects.IAttack;

public class Vampire extends GameObject{
	public static int vampireOnBoard;
	public static int vampireRemaining;
	public static int vampireDead;
	public static boolean vampireHasArrived;
	
	protected static final int damage = 1;
	private boolean moveAvaility;
	
	public Vampire(Game game, int x, int y) {
		super(game, x, y , 5);						//LA VIDA INICIAL ES 5			
		moveAvaility = false;
		vampireOnBoard += 1;
		vampireRemaining -= 1;
	}
	
	@Override
	public String toString() {
		return "V [" + life + "]";
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		if(!isDeath()) {
			IAttack other = game.getAttackbleInPosition(x, y -1);
			if(other != null) other.receiveVampireAttack(damage); 
		}	
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (!isDeath() && moveAvaility && game.checkIfPossitionIsEmpty(x, y - 1)) {
			y -= 1;
			if (y < 0) vampireHasArrived = true;
			moveAvaility = false;
		}		
		else moveAvaility = true;
	}
	
	public boolean receiveSlayerAttack(int damage) {
		life -= damage;
		if (life == 0) reduceAmount();
		return true;
	}
	
	public boolean receiveGarlicPush() {
		if (y == game.getDimensionX() - 1) {
			life = 0;
			reduceAmount();
		}
		else {
			if(game.checkIfPossitionIsEmpty(x, y + 1)) y += 1;
			moveAvaility = false;
		}
		return true;
	}
	
	public boolean receiveLightFlash() {
		life = 0;
		reduceAmount();
		return true;
	}

	public void reduceAmount() {								
		vampireOnBoard -= 1;
		vampireDead += 1;
	}

	
}
