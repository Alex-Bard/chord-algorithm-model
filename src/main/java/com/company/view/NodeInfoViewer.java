package com.company.view;

import com.company.busnesslogic.NodeInfoInt;
import org.w3c.dom.Node;

import java.util.stream.Collectors;

public class NodeInfoViewer implements NodeInfoViewerInt {
    private NodeInfoInt mainNode;
    public NodeInfoViewer(NodeInfoInt mainNode){
        this.mainNode = mainNode;
    }
    public String getNetworkStats(){
        NodeInfoInt currentNode = mainNode;
        StringBuilder res = new StringBuilder();
        do {
            res.append("Node id: " + currentNode.getId() + "\n");
            res.append("Node successor id: " + currentNode.getSuccessor().getId() + "\n");
            res.append("Node predecessor id: " + currentNode.getPredecessor().getId() + "\n");
            res.append("Node fingers: " + currentNode.getFingers()
                    .stream().map(f -> f.getId() + " ").collect(Collectors.joining()) + "\n");
        }while (currentNode.getId() != mainNode.getId());
        return res.toString();
    }
}