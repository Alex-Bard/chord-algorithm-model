package com.company.busnesslogic.node;

public interface NodeInt {
    int getId();
    void join(NodeInt nodeInChordRing);
    void stabilize();
    boolean isAlive();
    void off();
    void fixFingers();
}
