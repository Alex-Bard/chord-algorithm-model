package com.company.busnesslogic.node;

import com.company.busnesslogic.ChordRingInt;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Node implements NodeInfoInt, NodeInt {
    private final ChordRingInt network;
    private final int id;
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

    public void join(NodeInt nodeInChordRing){
        this.successor = ((Node)nodeInChordRing).findSuccessorFor(this.id);
        this.successor.notifyNode(this);
        fixFingers();
    }
    protected Node findSuccessorFor(int idNode){
        //range
        if (checkBelongingToTheInterval(this.id,this.successor.getId(),idNode)){
            return this.successor;
        }
        else{
           return findClosestPrecedingNodeFor(idNode).findSuccessorFor(idNode);
        }
    }
    protected Node findClosestPrecedingNodeFor(int idNode){
        for (int i = fingers.size() - 1; i >= 0; i--){
            if (checkBelongingToTheInterval(this.id,idNode,this.fingers.get(i).getId())){
                return this.fingers.get(i);
            }
        }
        return this;
    }
    public void stabilize(){
        this.checkSuccessor();
        this.checkPredecessor();
        if (this.predecessor == null) notifyNode(this);
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
        if (this.successor == null || !this.successor.isAlive){
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
        return new LinkedList<>(this.fingers);
    }

    public void off(){
        this.isAlive = false;
    }
    public void fixFingers(){
        this.fingers.clear();
        for (int i = 0 ; i < this.network.getM(); i ++){
            int fingerIndex = (int) (this.id + Math.pow(2, i) % Math.pow(2, this.network.getM()));
            fingerIndex %= Math.pow(2,network.getM());
            fingers.add( this.findSuccessorFor(fingerIndex));
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
        return idToCheck >= firstId && idToCheck <= secondIdForCheckBelonging;
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
