# Project Report

## Introduction

This project aims to develop a Java application that includes the following features:
- Importing data from external files
- Handling rectangles
- Merging nodes
- Providing a graphical user interface for drawing and managing nodes and trees

## Goals
1. Develop an interactive graphical user interface that is easy for users to operate.
2. Enable users to import data related to rectangles from external files and convert them to binary tree structures.
3. Develop classes and methods for adding, merging, and managing nodes (rectangles) within the binary tree.
4. Provide mechanisms for drawing the binary tree and displaying it visually to the users.
5. Implement features to verify the possibility of merging rectangles and provide feedback to users.
6. Allow users to export the binary tree data to external files.

## Classes Used

### Main Class
- **Function**: Set the main appearance of the application using `FlatLightLaf` and `Swing`.
- **Tasks**: 
  - Set the look and feel of the application using `Swing`.

### ImportFromFileGUI Class
- **Function**: Inherits from `JFrame` and implements `ActionListener`.
- **Tasks**:
  - Handle importing data from a file and converting it to a binary tree structure.
  - Include methods to convert data to rectangles, add rectangles to the tree, check for merges, and manage nodes.
  - Use the `BinaryTree` and `Node` classes for operations on the tree.

### Methods Class
- **Function**: Manage the display structure of the tree.
- **Tasks**:
  - Provide a visual representation of the tree structure.
  - Use `Swing` components to represent the graphical user interface.

### OutputTree Class
- **Function**: Inherits from `JFrame` and implements `ActionListener`.
- **Tasks**:
  - Handle importing data from a file and converting it to a binary tree structure.
  - Include methods to convert data to rectangles, add rectangles to the tree, check for merges, and manage nodes.
  - Use the `BinaryTree` and `Node` classes for operations on the tree.

### Sheet Class
- **Function**: Represent operations related to drawing sheets.
- **Tasks**:
  - Contain properties related to drawing sheets.
  - Be used for formatting other classes' graphical representations.

### Drawing Class
- **Function**: Handle drawing operations within the project.
- **Tasks**:
  - Manage drawing structures of binary trees.
  - Provide functions to visualize the nodes and connections in the tree.

### WelcomeInterface Class
- **Function**: Represent the welcome interface in the application.
- **Tasks**:
  - Handle interactions with the root node in the binary tree.
  - Provide a visual representation of the flipped nodes.

### PrintBinaryTreeOption Class
- **Function**: Manage printing options for binary tree structures.
- **Tasks**:
  - Handle different printing options for binary trees.
  - Support user interactions to select printing options.

### PrintBinaryTreeOption2 Class
- **Function**: Another class to manage printing options for binary tree structures.
- **Tasks**:
  - Provide additional functions for printing binary tree structures.
  - Support customization and selection of printing options.

### DrawingBinaryTree Class
- **Function**: Manage drawing operations related to binary tree structures.
- **Tasks**:
  - Provide a visual representation of binary trees.
  - Support visual representation of nodes and connections within the tree.

### FileDisplay Class
- **Function**: Manage the display window for nodes.
- **Tasks**:
  - Handle displaying contents of files within the application.
  - Support user interactions related to file functions.

### FlipWindow Class
- **Function**: Manage the welcome screen graphical user interface.
- **Tasks**:
  - Handle interactions with the root node in the binary tree.
  - Provide a visual representation of the flipped nodes.

## Data Structures Used

### BinaryTree Class
- **Function**: Represents the data structure for storing nodes in a binary tree.
- **Tasks**:
  - Support operations such as adding nodes, traversing the tree, and printing the tree.

### Node Class
- **Function**: Represents a node in the binary tree structure.
- **Tasks**:
  - Contain properties like name, width, height, etc., related to rectangles.
  - Be used for creating and managing individual nodes in the tree.

### ArrayList Class
- **Function**: Used for storing lists of nodes, merged nodes, inverted nodes, etc.
- **Tasks**:
  - Provide dynamic resizing and easy access to elements.
---