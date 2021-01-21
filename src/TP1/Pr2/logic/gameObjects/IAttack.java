package TP1.Pr2.logic.gameObjects;

public interface IAttack {

	void attack();
	
	default boolean receiveSlayerAttack(int damage) {return false;};
	default boolean receiveVampireAttack(int damage) {return false;};
	default boolean receiveLightFlash() {return false;};
	default boolean receiveGarlicPush() {return false;};
	default boolean receiveDraculaAttack() {return false;};
}
