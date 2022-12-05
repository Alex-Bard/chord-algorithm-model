package com.company.busnesslogic.node;

import java.util.List;

public interface NodeInfoInt {
    int getId();
    NodeInfoInt getPredecessor();
    NodeInfoInt getSuccessor();
    boolean isAlive();
    List<NodeInfoInt> getFingers();
}
