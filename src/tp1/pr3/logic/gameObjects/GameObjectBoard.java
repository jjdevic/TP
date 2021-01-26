package tp1.pr3.logic.gameObjects;


public class GameObjectBoard {
	private GameObjectList list;

	public GameObjectBoard() {
		initBoard();
	}
	
	public void initBoard() {
		list = new GameObjectList();
	}
	
	public String toString(int x, int y) {
		return list.toString(x, y);
	}
	
	public void add(GameObject gameObjects) {
		list.addObject(gameObjects);
	}
	
	public void move() {
		list.move();
	}
	
	public void attack() {
		list.attack();
	}
	
	public void remove() {
		list.remove();
	}
	
	public IAttack isInPosition(int x, int y) {
		return list.isInPosition(x, y);
	}

	public void garlicPush() {
		list.garlicPush();
	}

	public void lightFlash() {
		list.lightFlash();
	}
	
}
