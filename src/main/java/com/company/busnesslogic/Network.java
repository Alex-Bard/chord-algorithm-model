package main.java.com.company.busnesslogic;

public class Network implements ChordRingInt{

    private int M;

    @Override
    public int getUniqueId() {
        return 0;
    }

    @Override
    public int getM() {
        return this.M;
    }

    @Override
    public Node getNodeByIndex(int index) {
        return null;
    }

    @Override
    public boolean checkBelongingToTheInterval(int firstId, int secondId, int idToCheck) {
        int secondIdForCheckBelonging = secondId;
        if (secondId < firstId){
            secondIdForCheckBelonging += Math.pow(2,this.getM());
        }
        if (idToCheck > firstId && idToCheck < secondIdForCheckBelonging) return true;
        else return false;
    }
}
