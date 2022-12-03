package com.company;

import com.company.busnesslogic.Network;
import com.company.view.NodeInfoViewer;
import com.company.view.NodeInfoViewerInt;
import java.util.Scanner;

public class Main {
    private static Network network;
    private static NodeInfoViewerInt viewer;
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        while (true){
            String command;
            if (sc.hasNext()){
                command = sc.nextLine();
                if (isExit(command))
                    return;
                try {
                    processCommand(command);
                }
                catch (Exception e){
                    System.out.println("An error has occurred");
                    e.printStackTrace();
                }

            }
        }
    }
    private static boolean isExit(String command){
        if (command.equals("exit")) return true;
        else return false;
    }
    private static void processCommand(String command) {
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
                viewer = new NodeInfoViewer(network.getFirstNode());
                System.out.println("network initialized successfully");
                break;
            case "printNodesStats":
                System.out.println(viewer.getNetworkStats());
                break;
            case "stab":
                network.stabilize();
                System.out.println("network stabilize successfully");
                break;
            case "help":
                System.out.println("it's help)");
                break;
            default:
                System.out.println("Command not recognized. Please, use \"help\" to view list of commands");
                break;
        }
    }
}
