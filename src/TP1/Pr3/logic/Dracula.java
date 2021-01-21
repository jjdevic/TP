package TP1.Pr3.logic;

import TP1.Pr3.logic.gameObjects.IAttack;

public class Dracula extends Vampire{
	public static boolean isDracula;
	public static final String Symbol = "D";

	public Dracula(Game game, int x, int y) {
		super(game, x, y);
		isDracula = true;
	}

	@Override
	public String toString() {
		return "D [" + life + "]";
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
		isDracula = false;									//Este metodo existe porque isDeath() se ejecutaba dos veces y jodia el contador de vampiros
		vampireOnBoard -= 1;
		vampireDead += 1;
	}
}
