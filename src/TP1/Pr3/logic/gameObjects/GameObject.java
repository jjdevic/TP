package TP1.Pr3.logic.gameObjects;

import TP1.Pr3.logic.Game;

public abstract class GameObject implements IAttack{
	protected int x;
	protected int y;
	protected int life;
	protected Game game;
	
	public GameObject(Game game, int x, int y, int life) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.life = life;
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
	
	public abstract String toString();
	public abstract void move();
	public abstract void attack();
}
