package tp1.pr1;

import tp1.pr1.logic.Game;

public class VampireList {
	private final static int MAX_SLAYERS = 10;
	private Game game;
	private Vampire vampires[];
	private int nVampires;
	private int nVampiresAux;
	
	public VampireList(Game game) {
		this.game = game;
		vampires = new Vampire[MAX_SLAYERS];
		nVampires = 0;						//Se inicia a 0, es la variable que controlara el numero de vampiros que hay creados.
		nVampiresAux = 0;					//Variable de control extra
	}
	
	public void addVampire(int y, int nMaxVampires) {
		if(nVampiresAux < nMaxVampires) {
			vampires[nVampires] = new Vampire(y, game.getDimensionX() - 1);
			nVampires++;
			nVampiresAux++;
		}
	}
	
	public Vampire inPosition(int x, int y) {
		for(int i = 0; i < nVampires; i++) {
			if(vampires[i].isInPosition(x, y)) {
				return vampires[i];
			}
		}
		return null;
	}
	
	public boolean isInPosition(int x, int y) {
		return inPosition(x, y) != null;
	}
	
	public void vampiresMove() {
		if(nVampires > 0) {
			for(int i = 0; i < nVampires; i++) {
				vampires[i].move();
			}
		}
	}
	
	public void removeVampire() {
		for(int i = 0; i < nVampires; i++) {
			if(vampires[i].isDeath()) {
				vampires[i] = vampires[i+1];
				nVampires--;
			}
		}
	}
	
	public boolean vampireWins(int x) {
		for(int i = 0; i < nVampires; i++) {
			if(vampires[i].getY() <= 0) return true;
		}
		return false;
	}
	
	public int getNVampires() {
		return nVampires;
	}
	
	public int getNVampiresAux() {
		return nVampiresAux;
	}
	
	public void vampireAttack() {
		int x, y;
		for(int i = 0; i < nVampires; i++) {
			x = vampires[i].getX();
			y = vampires[i].getY();
			if(game.isSlayerInPosition(x, y)) {
				vampires[i].setAttack(true);
			}
			else vampires[i].setAttack(false);
		}
	}
	
	
	
}
