package com.mycompany.issue1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Methods {
    static List<Node> nodes = new ArrayList<>();
    static List<Map<Boolean, Node>> combinedNodes = new ArrayList<>();
    static List<String> combinedNodesNames = new ArrayList<>();
    static List<String> notCombinedNodes = new ArrayList<>();
    static int countCombinations = 0;

    public static List<Node> getNodes() {
        return nodes;
    }

    public static List<Map<Boolean, Node>> canCombineIntoSingleSheet(List<Node> sheets) {
        List<List<String>> combinations = generateCombinations(sheets);
        String name = new String();
        int[] arr = new int[2];

        for (int i = 0; i < combinations.size(); i++) {
            Map<Boolean, Node> results = canFormRectangle(getNodesFromNames(sheets, combinations.get(i)));
            if (results.containsKey(Boolean.TRUE)) {
                if (results.size() >= 2) {
                    combinedNodes.add(results);
                    name = results.get(Boolean.TRUE).getName();
                    arr[0] = results.get(Boolean.TRUE).getWidth();
                    arr[1] = results.get(Boolean.TRUE).getHeight();
                    // countCombinations += 1;
                    countCombinations();
                }
            }
        }
        for (Node sheet : sheets) {
            if (!combinedNodesNames.contains(sheet.getName())) {
                notCombinedNodes.add(sheet.getName());
            }
        }
        return combinedNodes;
    }

    private static List<List<String>> generateCombinations(List<Node> sheets) {
        List<List<String>> combinations = new ArrayList<>();
        int n = sheets.size();

        for (int i = 1; i < (1 << n); i++) {
            List<String> combination = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    combination.add(sheets.get(j).getName());
                }
            }
            if (combination.size() >= 2) {
                combinations.add(combination);
            }
        }
        return combinations;
    }

    private static List<Node> getNodesFromNames(List<Node> sheets, List<String> names) {
        List<Node> result = new ArrayList<>();
        for (String name : names) {
            for (Node sheet : sheets) {
                if (sheet.getName().equals(name)) {
                    result.add(sheet);
                    break;
                }
            }
        }
        return result;
    }

    private static Map<Boolean, Node> canFormRectangle(List<Node> nodes) {
        Map<Boolean, Node> result = new HashMap<>();
        int totalWidth = nodes.stream().mapToInt(node -> node.getWidth()).sum();
        int totalHeight = nodes.stream().mapToInt(node -> node.getHeight()).sum();
        int area = nodes.stream().mapToInt(node -> node.getWidth() * node.getHeight()).sum();
        String newName = nodes.stream().map(node -> node.getName()).reduce((a, b) -> a + b).orElse("");
        Node node = new Node(newName, totalWidth, totalHeight);

        for (Node sheet : nodes) {
            if (totalWidth == sheet.getWidth() || totalWidth == sheet.getHeight()) {
                result.put(Boolean.TRUE, node);
            }

            else if (totalHeight == sheet.getWidth() || totalHeight == sheet.getHeight()) {
                result.put(Boolean.TRUE, node);
            }

            else if (area % sheet.getWidth() == 0 && area / sheet.getWidth() == totalHeight) {
                result.put(Boolean.TRUE, node);
            }

            else if (area % sheet.getHeight() == 0 && area / sheet.getHeight() == totalWidth) {
                result.put(Boolean.TRUE, node);
            } else {
                result.put(false, null);
            }
        }
        return result;
    }

    public static Node flip(Node node) {
        String[] newName = node.getName().split("");
        String name = "";
        for (int i = newName.length - 1; i >= 0; i--) {
            name += newName[i];
        }
        int width = node.getWidth();
        int height = node.getHeight();

        int temp = width;
        width = height;
        height = temp;
        Node flippedNode = new Node(name, width, height);
        return flippedNode;
    }

    public static void countCombinations() {
        countCombinations += 1;
    }

    public static int getCountCombinations() {
        return countCombinations;
    }

    public static String printCombinations(String name, int[] arr, List<List<String>> combinedNodes,
            List<String> notCombinedNodes) {
        String result = "Name: " + name
                + "\nWidth= " + arr[0]
                + "\nHeight= " + arr[1]
                + "\nCombined Nodes: " + combinedNodes
                + "\nNot Combined Nodes: " + notCombinedNodes;
        return result;
    }

    public static BinaryTree importTree(File file) throws IOException {
        BinaryTree root = new BinaryTree();
        String input = "";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String data;
        while ((data = reader.readLine()) != null) {
            input += data;
        }
        System.out.println(input);
        reader.close();
        String pattern = "\\b([A-Za-z])(\\[([0-9]+),([0-9]+)\\])";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(input);

        while (m.find()) {
            Node node = new Node(m.group(1), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
            root.addLeaf(m.group(1), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
            nodes.add(node);
        }
        return root;
    }

    public static void exportTree(BinaryTree tree, File path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        String data = tree.printTree(tree.getRoot());
        writer.write(data);
        writer.flush();
        writer.close();
    }
}
