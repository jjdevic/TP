package tp1.pr3.logic.gameObjects;

import java.util.ArrayList;

public class GameObjectList {
	private ArrayList<GameObject> gameObjects;
	
	
	public GameObjectList() {
		gameObjects = new ArrayList<GameObject>();
	}
	
	public GameObject isInPosition (int x, int y) {
        for(GameObject o: gameObjects){
            if(x == o.getX() && y == o.getY()) {
            	if (!o.isDeath()) return o;
            }
        }
        return null;
    }
	
	public String toString(int x, int y) {
        if (isInPosition(x, y) == null) return "";
        
        return isInPosition(x, y).toString();
    }
	
	public void addObject(GameObject object) {
		gameObjects.add(object);
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
	
}
