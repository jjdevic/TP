package TP1.Pr4.logic;

import TP1.Pr4.logic.gameObjects.IAttack;

public class Dracula extends Vampire{
	public static boolean isDracula;
	public static final String Symbol = "D";

	public Dracula(Game game, int x, int y) {
		super(game, x, y);
		isDracula = true;
		symbol = "D";
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		if(!isDeath()) {
			IAttack other = game.getAttackbleInPosition(x, y -1);
			if(other != null) other.receiveDraculaAttack();
		}	
	}
	
	public boolean receiveLightFlash() {
		return false;
	}
	
	@Override
	public void reduceAmount() {
		super.reduceAmount();
		Dracula.isDracula = false;
	}
}
