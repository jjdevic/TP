package tp1.pr2.logic.gameObjects;

import tp1.pr2.logic.Game;

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
	
    public void reciveAttack(int damage) {
		life = life - damage;
	}
    
	public boolean isDeath() {
		return life == 0;
	}
	
	public abstract String toString();
	public abstract void move();
	public abstract void attack();
	public abstract void reduceAmount();
	
}
