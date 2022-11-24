package java.com.company.busnesslogic;

import java.util.*;

public class Network implements ChordRingInt{
    private Node firstNode;
    private Set<Integer> indexes;
    private int m;

    public Network(int m) {
        indexes = new HashSet<>();
        this.m = m;
        int numOfNodes = (int) Math.pow(2,m);
        for (int i = 1; i < numOfNodes; i++){
            indexes.add(i);
        }
        this.firstNode = new Node(0, this);
        this.firstNode.join(this.firstNode);
    }
    public void addNode(int nodeIndex){
        if (nodeIndex < 1 || nodeIndex > Math.pow(2,m))
            throw new IndexOutOfBoundsException("Index " + nodeIndex + " is out of range from 0 to " + Math.pow(2,m));
        if (this.indexes.contains(nodeIndex)){
            this.indexes.remove(nodeIndex);
        }
        else throw new IndexOutOfBoundsException("Node with index " + nodeIndex + "already exists");

        Node newNode = new Node(nodeIndex,this);
        newNode.join(this.firstNode);
    }

    @Override
    public int getUniqueId() {
        return 0;
    }

    @Override
    public int getM() {
        return this.m;
    }
}
