package TP1.Pr3.logic;

import TP1.Pr3.logic.gameObjects.GameObject;
import TP1.Pr3.logic.gameObjects.IAttack;

public class Slayer extends GameObject {
	public static final int cost = 50;
	private static final int damage = 1;
	
	public Slayer(Game game, int x, int y) {
		super(game, x, y, 3);								//LA VIDA INICIAL ES 3
	}
	
	@Override
	public String toString() {
		return "S [" + life + "]";
	}
	
	public int getCost() {
		return cost;
	}

	@Override
	public void attack() {
		if(!isDeath()) {
			for(int i = y; i < game.getDimensionX(); i++) {
				IAttack other = game.getAttackbleInPosition(x, i);
				if(other != null && other.receiveSlayerAttack(damage)) {
					break;
				}
			}
		}
	}

	public boolean receiveVampireAttack(int damage) {
		life -= damage;
		return true;
	}
	
	public boolean receiveDraculaAttack() {
		life = 0;
		return true;
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
	}

}
