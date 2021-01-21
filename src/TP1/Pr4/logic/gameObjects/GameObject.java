package TP1.Pr4.logic.gameObjects;

import TP1.Pr4.logic.Game;

public abstract class GameObject implements IAttack{
	protected int x;
	protected int y;
	protected int life;
	protected Game game;
	protected String symbol;
	
	public GameObject(Game game, int x, int y, int life, String symbol) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.life = life;
		this.symbol = symbol;
	}
	
	public String toString() {
		return symbol + " [" + life + "]";
	}
	
	public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isInPosition(int x, int y) {
		return this.x == x && this.y == y;
	}
    
	public boolean isDeath() {
		return life == 0;
	}
	
	public String serialize() {
		return symbol + ";" + y + ";" + x + ";" + life + "\n";
	}
	
	public abstract void move();
	public abstract void attack();
}
