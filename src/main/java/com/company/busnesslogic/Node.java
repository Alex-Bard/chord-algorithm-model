package main.java.com.company.busnesslogic;

import java.util.List;

public class Node {
    private ChordRingInt network;
    private int id;
    private boolean isAlive;
    private Node predecessor;
    private Node successor;
    private List<Node> fingers;

    public int getId() {
        return id;
    }

    public Node getSuccessor() {
        return successor;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public Node(ChordRingInt network) {
        this.id = network.getUniqueId();//check word
        this.network = network;
        this.isAlive = true;
    }

    public void join(Node nodeInChordRing){
        this.successor = nodeInChordRing.findSuccessorFor(this.id);
    }
    public Node findSuccessorFor(int idNode){
        //range
        if (network.checkBelongingToTheInterval(this.id,this.successor.getId() + 1,idNode)){
            return this.successor;
        }
        else{
           return findClosestPrecedingNodeFor(idNode).findSuccessorFor(idNode);
        }
    }
    public Node findClosestPrecedingNodeFor(int idNode){
        int m = this.network.getM();
        for (int i = m; i <= 0; i--){
            if (network.checkBelongingToTheInterval(this.id,idNode,this.fingers.get(i).getId())){
                return this.fingers.get(i);
            }
        }
        return this;
    }
    public void stabilize(){
        int idPredecessorForSuccessor = this.successor.getPredecessor().getId();
        if (network.checkBelongingToTheInterval(this.id,this.successor.getId(),idPredecessorForSuccessor)){
            this.successor = this.successor.getPredecessor();
            this.successor.notifyNode(this);
        }
    }
    public void notifyNode(Node newPredecessor){
        int newPredecessorId = newPredecessor.getId();
        if (this.predecessor == null
                || network.checkBelongingToTheInterval(this.predecessor.getId(),this.id,newPredecessorId)){
            this.predecessor = newPredecessor;
        }
    }
    public void checkPredecessor(){
        if (!this.predecessor.isAlive()){
            this.predecessor = null;
        }
    }
    public boolean isAlive(){
        return false;
    }
    public void off(){
        this.isAlive = false;
    }
    public void fixFingers(){
        int next = 0;
        for (int i = 0 ; i < this.network.getM(); i ++){
            next += 1;

            int fingerIndex = (int) (this.id + Math.pow(2, i) % Math.pow(2, this.network.getM()));
             fingers.add(network.getNodeByIndex(fingerIndex));
        }
    }
}
