package TP1.Pr1;

public class Slayer {
	private int x;		//COLUMNAS
	private int y;		//FILAS
	private int life;
	private int cost;
	
	
	public Slayer(int x, int y) {
		this.x = x;
		this.y = y;
		life = 3;
		cost = 50;
	}
	
	public String toString() {
		return "S[" + life + "]";
	}
	
	public void reciveAttack(int damage) {
		this.life = life - damage;
	}
	
	public boolean isInPosition(int x, int y) {
		return this.x == x && this.y == y;
	}
	
	public boolean isDeath() {
		return life == 0;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public int getCost() {
		return cost;
	}
	
}
