package TP1.Pr2.logic;

import TP1.Pr2.logic.gameObjects.GameObject;
import TP1.Pr2.logic.gameObjects.IAttack;

public class Vampire extends GameObject{
	public static int vampireOnBoard;
	public static int vampireRemaining;
	public static int vampireDead;
	public static boolean vampireHasArrived;
	
	private static final int damage = 1;
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
		if (!isDeath() && moveAvaility && game.getAttackbleInPosition(x, y - 1) == null) {
			y = y - 1;
			if (y < 0) vampireHasArrived = true;
			moveAvaility = false;
		}		
		else moveAvaility = true;
	}
	
	public boolean receiveSlayerAttack(int damage) {
		life -= damage;
		return true;
	}

	@Override
	public void reduceAmount() {		//Este metodo existe porque isDeath() se ejecutaba dos veces y jodia el contador de vampiros
		vampireOnBoard -= 1;
		vampireDead += 1;
	}
	
	
	
}
