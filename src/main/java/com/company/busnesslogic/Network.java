package com.company.busnesslogic;

import java.util.*;

public class Network implements ChordRingInt{
    private Set<Node> nodes;
    private Node firstNode;
    private int m;

    public Network(int m) {
        this.nodes = new LinkedHashSet<>();
        this.m = m;
        this.firstNode = new Node(0, this);
        this.nodes.add(firstNode);
        this.firstNode.setSuccessor(firstNode);
        this.firstNode.setPredecessor(firstNode);
        this.stabilize();
    }
    public void addNode(int nodeIndex){
        Node nodeToAdd = new Node(nodeIndex,this);
        if (nodeIndex < 1 || nodeIndex > Math.pow(2,m))
            throw new IndexOutOfBoundsException("Index " + nodeIndex + " is out of range from 0 to " + Math.pow(2,m));
        if (this.nodes.contains(nodeToAdd))
            throw new IllegalArgumentException("Node with index " + nodeIndex + " already exists");
        nodeToAdd.join(this.firstNode);
        this.nodes.add(nodeToAdd);
        this.stabilize();
    }
    public void stabilize(){
        Iterator<Node> nodeIterator = this.nodes.iterator();
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
        if (idNode < 1 || idNode > Math.pow(2,m))
            throw new IndexOutOfBoundsException("Index " + idNode + " is out of range from 0 to " + Math.pow(2,m));
        if (!this.nodes.contains(nodeToRemove))
            throw new IllegalArgumentException("Node with index " + idNode + " not found");
        for (Node node : this.nodes){
            if (node.equals(nodeToRemove)) {
                node.off();
                this.nodes.remove(node);
            }
        }
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
    public Node getFirstNode() {
        return this.firstNode;
    }
}
