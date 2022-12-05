package com.company.view;

import com.company.busnesslogic.node.NodeInfoInt;

import java.util.stream.Collectors;

public class NodeInfoViewer implements NodeInfoViewerInt {
    private final NodeInfoInt mainNode;
    public NodeInfoViewer(NodeInfoInt mainNode){
        this.mainNode = mainNode;
    }
    public String getNetworkStats(){
        NodeInfoInt currentNode = mainNode;
        StringBuilder res = new StringBuilder();
        do {
            res.append("Node id: " + currentNode.getId() + "\n");
            res.append("    successor id: " + currentNode.getSuccessor().getId() + "\n");
            res.append("    predecessor id: " + currentNode.getPredecessor().getId() + "\n");
            res.append("    fingers: " + currentNode.getFingers()
                    .stream().map(f -> f.getId() + " ").collect(Collectors.joining()) + "\n");
            currentNode = currentNode.getSuccessor();
        }while (currentNode.getId() != mainNode.getId());
        return res.toString();
    }
}
