package tp1.pr1;

public class Vampire {
	private int x;		//COLUMNAS
	private int y;		//FILAS
	private int life;
	private int cont;
	private boolean attack;
	
	public Vampire(int x, int y) {
		this.x = x;
		this.y = y;							//Los vampiros siempre se crean en la primera columna
		life = 5;
		cont = 1;
		attack = false;
	}
	
	public String toString() {
		return "V[" + life + "]";
	}
	
	public boolean isInPosition(int x, int y) {
		return this.x == x && this.y == y;
	}
	
	public void move() {
		if (!isDeath() && cont % 2 == 0 && !attack) {
			y = y - 1;
		}
		cont++;
	}
	
	public boolean moveAvaility() {
		return true;
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
	
	public void reciveAttack(int damage) {
		this.life = life - damage;
	}
	
	public void setAttack(boolean ataque) {
		attack = ataque;
	}
	
}
