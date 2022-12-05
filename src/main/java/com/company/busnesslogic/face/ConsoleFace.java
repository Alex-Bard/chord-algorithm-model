package com.company.busnesslogic.face;

import com.company.busnesslogic.ChordRingInt;
import com.company.busnesslogic.Network;
import com.company.busnesslogic.node.NodeInfoInt;
import com.company.view.NodeInfoViewer;
import com.company.view.NodeInfoViewerInt;

import java.util.Scanner;

public class ConsoleFace {
    private  ChordRingInt network;
    private NodeInfoViewerInt viewer;
    private final Scanner sc = new Scanner(System.in);

    public void start(){
        System.out.println("Hello! To get help use \"help\" command");
        while (true){
            System.out.println("write command: ");
            String command;
            if (sc.hasNext()){
                command = sc.nextLine();
                if (isExit(command))
                    return;
                try {
                    processCommand(command);
                }
                catch (IndexOutOfBoundsException | IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
                catch (Exception e){
                    if (network != null){
                        System.out.println("Sorry, un error occurred");
                        return;
                    }
                    else System.out.println("first you need to init chord rind using init command");

                }
            }
        }
    }
    private boolean isExit(String command){
        if (command.equals("exit")) return true;
        else return false;
    }
    private void processCommand(String command) {
        String[] args = command.split(" ");
        switch (args[0]) {
            case "addNode":
                network.addNode(Integer.parseInt(args[1]));
                System.out.println("node added successfully");
                break;
            case "offNode":
                network.removeNode(Integer.parseInt(args[1]));
                System.out.println("node offed successfully");
                break;
            case "init":
                network = new Network(Integer.parseInt(args[1]));
                viewer = new NodeInfoViewer((NodeInfoInt) network.getFirstNode());
                System.out.println("network initialized successfully");
                break;
            case "printNodes":
                System.out.println(viewer.getNetworkStats());
                break;
            case "help":
                System.out.println("app supports following commands:" + "\n"
                        + "\"help\" to get help" + "\n" +
                        "\"addNode {id}\" to add node to chord ring" + "\n" +
                        "\"offNode {id}\" to disable a node" + "\n" +
                        "\"printNodes\" to print nodes in chord ring" + "\n" +
                        "\"init {m}\" to init chord ring with m param" + "\n");
                break;
            default:
                System.out.println("Command not recognized. Please, use \"help\" to view list of commands");
                break;
        }
    }
}
