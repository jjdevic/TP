package tp1.pr1;

import tp1.pr1.logic.Game;

public class SlayerList {
	private final static int MAX_VAMP = 50;
	private Slayer slayers[];
	private int nSlayers;
	private Game game;
	
	public SlayerList(Game game) {
		this.game = game;
		slayers = new Slayer[MAX_VAMP];
		nSlayers = 0;
	}
	
	public void addSlayer(int x, int y) {
		slayers[nSlayers] = new Slayer(x, y);
		nSlayers++;
	}
	
	public Slayer inPosition(int x, int y) {
		for(int i = 0; i < nSlayers; i++) {
			if(slayers[i].isInPosition(x, y)) {
				return slayers[i];
			}
		}
		return null;
	}
	
	public boolean isInPosition(int x, int y) {
		return inPosition(x, y) != null;
	}
	
	public void removeSlayer() {
		for(int i = 0; i < nSlayers; i++) {
			if(slayers[i].isDeath()) {
				slayers[i] = slayers[i+1];
				nSlayers--;
			}
		}
	}
	
	public void slayerAttack() {
		int x, y;
		for(int i = 0; i < nSlayers; i++) {
			x = slayers[i].getX();
			y = slayers[i].getY();
			for(int j = y; j >= 0; j--) {
				game.isVampireInPosition(x, j);
			}
		}
	}
	
	public int getNSlayers() {
		return nSlayers;
	}
	
}
