package com.company.busnesslogic;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Node implements NodeInfoInt {
    private ChordRingInt network;
    private int id;
    private boolean isAlive;
    private Node predecessor;
    private Node successor;
    private List<Node> fingers;

    public int getId() {
        return id;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public Node getSuccessor() {
        return successor;
    }

    protected void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    protected void setSuccessor(Node successor) {
        this.successor = successor;
    }

    public Node(ChordRingInt network) {
        this.id = network.getUniqueId();
        this.network = network;
        this.isAlive = true;
    }
    public Node(int id, ChordRingInt network) {
        this.fingers = new LinkedList<>();
        this.id = id;
        this.network = network;
        this.isAlive = true;
    }

    public void join(Node nodeInChordRing){
        this.successor = nodeInChordRing.findSuccessorFor(this.id);
        this.successor.notifyNode(this);
        fixFingers();
    }
    public Node findSuccessorFor(int idNode){
        //range
        if (checkBelongingToTheInterval(this.id,this.successor.getId(),idNode)){
            return this.successor;
        }
        else{
           return findClosestPrecedingNodeFor(idNode).findSuccessorFor(idNode);
        }
    }
    public Node findClosestPrecedingNodeFor(int idNode){
        int m = this.network.getM();
        for (int i = m - 1; i >= 0; i--){
            if (checkBelongingToTheInterval(this.id,idNode,this.fingers.get(i).getId())){
                return this.fingers.get(i);
            }
        }
        return this;
    }
    public void stabilize(){
        this.checkSuccessor();
        this.checkPredecessor();
        int idPredecessorForSuccessor = this.successor.getPredecessor().getId();
        if (checkBelongingToTheInterval(this.id,this.successor.getId(),idPredecessorForSuccessor)){
            this.successor = this.successor.getPredecessor();
            this.successor.notifyNode(this);
        }
    }
    private void notifyNode(Node newPredecessor){
        int newPredecessorId = newPredecessor.getId();
        if (this.predecessor == null
                || checkBelongingToTheInterval(this.predecessor.getId(),this.id,newPredecessorId)){
            this.predecessor = newPredecessor;
        }
    }
    private void checkPredecessor(){
        if (!this.predecessor.isAlive()){
            this.predecessor = null;
        }
    }
    private void checkSuccessor(){
        if (!this.successor.isAlive){
            for (Node finger : this.fingers){
                if (finger.getId() != this.successor.getId()) {
                    this.successor = finger;
                    this.successor.notifyNode(this);
                    return;
                }
            }
            //if in circle one node
            this.successor = this;
            this.predecessor = this;
        }
    }
    public boolean isAlive(){
        return this.isAlive;
    }

    @Override
    public List<NodeInfoInt> getFingers() {
        List<NodeInfoInt> result = new LinkedList<>();
        result.addAll(this.fingers);
        return result;
    }

    public void off(){
        this.isAlive = false;
    }
    public void fixFingers(){
        //this.fingers.clear();
        for (int i = 0 ; i < this.network.getM(); i ++){
            int fingerIndex = (int) (this.id + Math.pow(2, i) % Math.pow(2, this.network.getM()));
             fingers.add(i, this.findSuccessorFor(fingerIndex));
        }
    }
    protected boolean checkBelongingToTheInterval(int firstId, int secondId, int idToCheck) {
        int secondIdForCheckBelonging = secondId;
        if (secondId <= firstId){
            secondIdForCheckBelonging += Math.pow(2,network.getM());
        }
         if (idToCheck <= firstId){
             idToCheck += Math.pow(2,network.getM());
         }
        if (idToCheck >= firstId && idToCheck <= secondIdForCheckBelonging) return true;
        else return false;
    }
    protected void addFinger(Node finger){
        this.fingers.add(finger);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
