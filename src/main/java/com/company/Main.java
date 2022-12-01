package com.company;

import com.company.busnesslogic.Network;
import com.company.view.NodeInfoViewer;
import com.company.view.NodeInfoViewerInt;

import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Scanner;

public class Main {
    private static Network network;
    private static NodeInfoViewerInt viewer;
    private static Scanner sc = new Scanner(System.in);
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
        if (command.equals("exit")){
            return true;
        }
        else return false;
    }
    private static void processCommand(String command) throws Exception{
        String[] args = command.split(" ");
        if (args[0].equals("addNode")){
            network.addNode(Integer.parseInt(args[1]));
        }else if(args[0].equals("offNode")){
            network.removeNode(Integer.parseInt(args[1]));
        }
        else if(args[0].equals("init")){
            network = new Network(Integer.parseInt(args[1]));
            viewer = new NodeInfoViewer(network.getFirstNode());
            System.out.println("network initialized successfully");
        }
        else if (args[0].equals("printNodesStats")){
            System.out.println(viewer.getNetworkStats());
        }
        else if (args[0].equals("help")){
            System.out.println("it's help)");
        }
        else {
            System.out.println("Command not recognized. Please, use \"help\" to view list of commands");
        }
    }
}
