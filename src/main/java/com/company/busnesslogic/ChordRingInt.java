package com.company.busnesslogic;

import com.company.busnesslogic.node.NodeInt;

public interface ChordRingInt {
    int getUniqueId();
    int getM();
    NodeInt getFirstNode();
    void addNode(int nodeIndex);
    void removeNode(int idNode);
}
