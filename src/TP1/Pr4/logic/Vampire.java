package TP1.Pr4.logic;

import TP1.Pr4.logic.gameObjects.GameObject;
import TP1.Pr4.logic.gameObjects.IAttack;

public class Vampire extends GameObject{
	public static int vampireOnBoard;
	public static int vampireRemaining;
	public static int vampireDead;
	public static boolean vampireHasArrived;
	
	protected static final int damage = 1;
	private int moveCont;
	
	public Vampire(Game game, int x, int y) {
		super(game, x, y , 5, "V");						//LA VIDA INICIAL ES 5			
		moveCont = 2;
		vampireOnBoard += 1;
		vampireRemaining -= 1;
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
		moveCont--;
		if (!isDeath() && moveCont <= 0 && game.checkIfPossitionIsEmpty(x, y - 1)) {
			y -= 1;
			if (y < 0) vampireHasArrived = true;
			moveCont = 2;
		}		
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
			moveCont = 2;
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
	
	@Override
	public String serialize() {
		return symbol + ";" + y + ";" + x + ";" + life + ";" + (moveCont - 1) + "\n";					//En comparacion a los test, mi contador siempre esta +1
	}

	
}
