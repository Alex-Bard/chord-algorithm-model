package com.company.busnesslogic;

import com.company.busnesslogic.node.Node;
import com.company.busnesslogic.node.NodeInt;

import java.util.*;

public class Network implements ChordRingInt{
    private Set<NodeInt> nodes;
    private final NodeInt firstNode;
    private final int m;

    public Network(int m) {
        this.nodes = new LinkedHashSet<>();
        this.m = m;
        this.firstNode = new Node(0, this);
        this.nodes.add(firstNode);
        this.stabilize();
    }
    public void addNode(int nodeIndex){
        Node nodeToAdd = new Node(nodeIndex,this);
        if (nodeIndex < 1 || nodeIndex >= Math.pow(2,m))
            throw new IndexOutOfBoundsException("Index " + nodeIndex + " is out of range from 1 to "
                    + (int)(Math.pow(2,m)));
        if (this.nodes.contains(nodeToAdd))
            throw new IllegalArgumentException("Node with index " + nodeIndex + " already exists");
        nodeToAdd.join(this.firstNode);
        this.nodes.add(nodeToAdd);
        this.stabilize();
        this.stabilize();
    }
    public void stabilize(){
        Iterator<NodeInt> nodeIterator = this.nodes.iterator();
        while (nodeIterator.hasNext()){
            nodeIterator.next().stabilize();
        }
        nodeIterator = this.nodes.iterator();
        while (nodeIterator.hasNext()){
            nodeIterator.next().fixFingers();
        }
    }
    public void removeNode(int idNode){
        Node nodeToRemove = new Node(idNode,this);
        List<NodeInt> nodesFromSet = new LinkedList<>(this.nodes);
        if (idNode < 1 || idNode >= Math.pow(2,m))
            throw new IndexOutOfBoundsException("Index " + idNode + " is out of range from 1 to "
                    + (int)(Math.pow(2,m) - 1));
        if (!this.nodes.contains(nodeToRemove))
            throw new IllegalArgumentException("Node with index " + idNode + " not found");
        for (int i = 0; i < nodesFromSet.size(); i++){
            if (nodesFromSet.get(i).equals(nodeToRemove)){
                nodesFromSet.get(i).off();
                nodesFromSet.remove(i);
            }
        }
        this.nodes = new LinkedHashSet<>(nodesFromSet);
        this.stabilize();
        this.stabilize();
    }

    @Override
    public int getUniqueId() {
        return 0;
    }

    @Override
    public int getM() {
        return this.m;
    }

    @Override
    public NodeInt getFirstNode() {
        return this.firstNode;
    }
}
