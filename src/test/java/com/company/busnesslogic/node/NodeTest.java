package com.company.busnesslogic.node;

import com.company.busnesslogic.ChordRingInt;
import com.company.busnesslogic.Network;
import com.company.busnesslogic.node.Node;
import com.company.busnesslogic.node.NodeInfoInt;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class NodeTest {

    @Test
    void findClosestPrecedingNodeFor() {
        ChordRingInt networkMock = Mockito.mock(Network.class);
        Mockito.when(networkMock.getM()).thenReturn(3);
        Node mainNode = new Node(0, networkMock);
        List<Node> nodes = new LinkedList<>();
        nodes.add(new Node(2,networkMock));
        nodes.add(new Node(2,networkMock));
        nodes.add(new Node(4,networkMock));
        for (Node node: nodes)
            mainNode.addFinger(node);

        assertEquals(4, mainNode.findClosestPrecedingNodeFor(5).getId());
        assertEquals(2, mainNode.findClosestPrecedingNodeFor(3).getId());

        mainNode = new Node(6,networkMock);
        nodes.clear();
        nodes.add(new Node(7,networkMock));
        nodes.add(new Node (7,networkMock));
        nodes.add(new Node(2,networkMock));
        for (Node node: nodes)
            mainNode.addFinger(node);

        assertEquals(7, mainNode.findClosestPrecedingNodeFor(0).getId());
        assertEquals(7, mainNode.findClosestPrecedingNodeFor(1).getId());
        assertEquals(2, mainNode.findClosestPrecedingNodeFor(3).getId());
        assertEquals(2, mainNode.findClosestPrecedingNodeFor(5).getId());

    }

    @Test
    void checkBelongingToTheInterval() {
        ChordRingInt networkMock = Mockito.mock(Network.class);
        Mockito.when(networkMock.getM()).thenReturn(3);
        Node mainNode= new Node(0,networkMock);
        assertTrue(mainNode.checkBelongingToTheInterval(0, 3, 2));
        assertFalse(mainNode.checkBelongingToTheInterval(0, 3, 4));
        assertTrue(mainNode.checkBelongingToTheInterval(6, 2, 0));
        assertFalse(mainNode.checkBelongingToTheInterval(6, 2, 3));
        assertTrue(mainNode.checkBelongingToTheInterval(6, 0, 7));
        assertTrue(mainNode.checkBelongingToTheInterval(0, 0, 7));
    }

    @Test
    void findSuccessorFor() {
        ChordRingInt networkMock = Mockito.mock(Network.class);
        Mockito.when(networkMock.getM()).thenReturn(3);

        Node mainNode = new Node(0, networkMock);
        mainNode.setSuccessor(mainNode);
        mainNode.setPredecessor(mainNode);
        assertEquals(0, mainNode.findSuccessorFor(1).getId());


        mainNode = new Node(0, networkMock);
        Node node2 = new Node(2,networkMock);

        mainNode.setSuccessor(node2);
        mainNode.setPredecessor(node2);
        node2.setPredecessor(mainNode);
        node2.setSuccessor(mainNode);

        mainNode.addFinger(node2);
        mainNode.addFinger(node2);
        mainNode.addFinger(mainNode);

        assertEquals(2, mainNode.findSuccessorFor(1).getId());
        assertEquals(0, mainNode.findSuccessorFor(3).getId());
        //assertEquals(2, mainNode.findSuccessorFor(2).getId());

        Node node4 = new Node(4,networkMock);
        Node node6 = new Node(6,networkMock);
        mainNode = new Node(0, networkMock);
        node2 = new Node(2,networkMock);

        mainNode.setSuccessor(node2);
        mainNode.setPredecessor(node6);
        mainNode.addFinger(node2);
        mainNode.addFinger(node2);
        mainNode.addFinger(node4);

        node2.setSuccessor(node4);
        node2.setPredecessor(mainNode);
        node2.addFinger(node4);
        node2.addFinger(node4);
        node2.addFinger(node6);

        node4.setSuccessor(node6);
        node4.setPredecessor(node2);
        node4.addFinger(node6);
        node4.addFinger(node6);
        node4.addFinger(mainNode);

        node6.setSuccessor(mainNode);
        node6.setPredecessor(node4);
        node6.addFinger(mainNode);
        node6.addFinger(mainNode);
        node6.addFinger(node2);

        assertEquals(0, mainNode.findSuccessorFor(7).getId());
        assertEquals(4, mainNode.findSuccessorFor(3).getId());
        assertEquals(6, mainNode.findSuccessorFor(5).getId());
    }

    @Test
    void fixFingers() {
        ChordRingInt networkMock = Mockito.mock(Network.class);
        Mockito.when(networkMock.getM()).thenReturn(3);

        Node mainNode = new Node(0, networkMock);
        Node node2 = new Node(2,networkMock);

        mainNode.setSuccessor(node2);
        mainNode.setPredecessor(node2);
        node2.setPredecessor(mainNode);
        node2.setSuccessor(mainNode);

        mainNode.addFinger(mainNode);
        mainNode.addFinger(mainNode);
        mainNode.addFinger(mainNode);

        mainNode.fixFingers();
        List<Integer> expected = new LinkedList<>();
        expected.add(2);
        expected.add(2);
        expected.add(0);
        assertEquals(expected, mainNode.getFingers().stream()
                .mapToInt(NodeInfoInt::getId).boxed()
                .collect(Collectors.toList()));

        expected.clear();
        expected.add(0);
        expected.add(0);
        expected.add(0);
        node2.fixFingers();
        assertEquals(expected, node2.getFingers().stream()
                .mapToInt(NodeInfoInt::getId).boxed()
                .collect(Collectors.toList()));

        mainNode = new Node(0, networkMock);
        node2 = new Node(5,networkMock);

        mainNode.setSuccessor(node2);
        mainNode.setPredecessor(node2);
        node2.setPredecessor(mainNode);
        node2.setSuccessor(mainNode);

        mainNode.addFinger(mainNode);
        mainNode.addFinger(mainNode);
        mainNode.addFinger(mainNode);

        mainNode.fixFingers();
        expected = new LinkedList<>();
        expected.add(5);
        expected.add(5);
        expected.add(5);
        assertEquals(expected, mainNode.getFingers().stream()
                .mapToInt(NodeInfoInt::getId).boxed()
                .collect(Collectors.toList()));

        expected.clear();
        expected.add(0);
        expected.add(0);
        expected.add(5);
        node2.fixFingers();
        assertEquals(expected, node2.getFingers().stream()
                .mapToInt(NodeInfoInt::getId).boxed()
                .collect(Collectors.toList()));
    }
}