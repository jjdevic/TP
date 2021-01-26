package tp1.prFinal.logic.gameObjects;

import java.util.ArrayList;

public class GameObjectList {
	private ArrayList<GameObject> gameObjects;
	
	
	public GameObjectList() {
		gameObjects = new ArrayList<GameObject>();
	}
	
	private GameObject getObjectInPosition(int x, int y) {
        for(GameObject o: gameObjects){
            if(x == o.getX() && y == o.getY()) {
            	if (!o.isDeath()) return o;
            }
        }
        return null;
    }
	
	public IAttack getAttacableInPosition(int x, int y) {
		return getObjectInPosition(x, y);
	}
	
	public boolean isObjectInPosition(int x, int y) {
		return getObjectInPosition(x, y) == null;
	}
	
	public String toString(int x, int y) {
        if (getObjectInPosition(x, y) == null) return "";
        
        return getObjectInPosition(x, y).toString();
    }
	
	public void addObject(GameObject o) {
		gameObjects.add(o);
	}
	
	public void move() {
		for(GameObject o: gameObjects) o.move();
	}
	
	public void attack() {
		for(GameObject o: gameObjects) o.attack();
	}
	
	public void remove() {
		for(int i = 0; i < gameObjects.size(); i++) {
			if(gameObjects.get(i).isDeath()) {
				gameObjects.remove(i);
			}
		}
	}

	public void garlicPush() {
		for(GameObject o: gameObjects) o.receiveGarlicPush();
	}

	public void lightFlash() {
		for(GameObject o: gameObjects) o.receiveLightFlash();		
	}
	
	public String serialize() {
		String aux= "";
		for(GameObject o: gameObjects) aux = aux + o.serialize();
		return aux;
	}
	
}
