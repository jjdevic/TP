package tp1.prFinal.logic.gameObjects;

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
	
	public IAttack getAttacableInPosition(int x, int y) {
		return list.getAttacableInPosition(x, y);
	}
	
	public boolean isObjectInPosition(int x, int y) {
		return list.isObjectInPosition(x, y);
	}

	public void garlicPush() {
		list.garlicPush();
	}

	public void lightFlash() {
		list.lightFlash();
	}
	
	public String serialize() {
		return list.serialize();
	}
}
