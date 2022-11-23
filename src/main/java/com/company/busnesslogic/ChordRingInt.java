package main.java.com.company.busnesslogic;

public interface ChordRingInt {
    int getUniqueId();
    int getM();
    Node getNodeByIndex(int index);
    boolean checkBelongingToTheInterval(int firstId, int secondId,int idToCheck);
}
