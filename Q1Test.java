import java.util.*;
public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here

      // Node class for the BST
static class Node {
    int value;
    Node left, right;

    Node(int value) {
        this.value = value;
    }
}

// Insert a value into the BST
private static Node insert(Node root, int value) {
    if (root == null) {
        return new Node(value);
    }

    if (value < root.value) {
        root.left = insert(root.left, value);
    } else if (value > root.value) {
        root.right = insert(root.right, value);
    }

    return root;
}

// Inorder: Left -> Root -> Right
private static void inorder(Node root, List<Integer> result) {
    if (root == null) return;

    inorder(root.left, result);
    result.add(root.value);
    inorder(root.right, result);
}

// Preorder: Root -> Left -> Right
private static void preorder(Node root, List<Integer> result) {
    if (root == null) return;

    result.add(root.value);
    preorder(root.left, result);
    preorder(root.right, result);
}

// Postorder: Left -> Right -> Root
private static void postorder(Node root, List<Integer> result) {
    if (root == null) return;

    postorder(root.left, result);
    postorder(root.right, result);
    result.add(root.value);
}

// Convert a string into integer array
private static int[] parse(String traversal) {
    String[] parts = traversal.split("-");
    int[] values = new int[parts.length];

    for (int i = 0; i < parts.length; i++) {
        values[i] = Integer.parseInt(parts[i].trim());
    }

    return values;
}

// Check whether the sequence is strictly increasing
private static boolean isSorted(int[] values) {
    for (int i = 1; i < values.length; i++) {
        if (values[i] <= values[i - 1]) {
            return false;
        }
    }

    return true;
}

// Check whether the traversal matches the generated sequence
private static boolean matches(int[] values, List<Integer> result) {
    if (values.length != result.size()) return false;

    for (int i = 0; i < values.length; i++) {
        if (values[i] != result.get(i)) {
            return false;
        }
    }

    return true;
}
     public static String verify(String traversal1, String traversal2, String traversal3){int[][] traversals = new int[3][];

    //Parse the input strings
    try {
        traversals[0] = parse(traversal1);
        traversals[1] = parse(traversal2);
        traversals[2] = parse(traversal3);
    } catch (NumberFormatException e) {
        return "Invalid traversals";
    }

    int n = traversals[0].length;

    //Check lengths, duplicates and values
    Set<Integer> expected = new HashSet<>();

    for (int value : traversals[0]) {
        if (!expected.add(value)) {
            return "Invalid traversals";
        }
    }

    for (int i = 1; i < 3; i++) {
        if (traversals[i].length != n) {
            return "Invalid traversals";
        }

        Set<Integer> current = new HashSet<>();

        for (int value : traversals[i]) {
            if (!current.add(value)) {
                return "Invalid traversals";
            }
        }

        if (!current.equals(expected)) {
            return "Invalid traversals";
        }
    }

    // Try each sequence as inorder
    for (int in = 0; in < 3; in++) {

        // Sort for inorder
        if (!isSorted(traversals[in])) {
            continue;
        }

        // Try remaining sequences as preorder
        for (int pre = 0; pre < 3; pre++) {

            if (pre == in) continue;

            // Remaining index is postorder
            int post = 3 - in - pre;

            // Build a BST using the assumed preorder
            Node root = null;

            for (int value : traversals[pre]) {
                root = insert(root, value);
            }

            // Generate and store the traversals
            List<Integer> inResult = new ArrayList<>();
            List<Integer> preResult = new ArrayList<>();
            List<Integer> postResult = new ArrayList<>();
            inorder(root, inResult);
            preorder(root, preResult);
            postorder(root, postResult);

            // Verify the traversals
            if (matches(traversals[in], inResult)
                    && matches(traversals[pre], preResult)
                    && matches(traversals[post], postResult)) {

                String[] types = new String[3];

                types[in] = "Inorder";
                types[pre] = "Preorder";
                types[post] = "Postorder";

                return "Traversal 1 - " + types[0]
                        + ", Traversal 2 - " + types[1]
                        + ", Traversal 3 - " + types[2];
            }
        }
    }

    return "Invalid traversals";
}
}