import java.util.List;

interface Tree<E extends Comparable<E>> extends TreePrinter.PrintableNode {
    // Access fields
    BinaryNode<E> root();

    // Basic properties
    int height();
    boolean isBalanced();
    void updateHeight();
    int size();

    // Traversals that return lists
    List<E> preOrderList();
    List<E> inOrderList();
    List<E> postOrderList();

    // Helpers for BST/AVL methods
    BinaryNode<E> extractRightMost(BinaryNode<E> curNode);

    // AVL & BST Search & insert same
    BinaryNode<E> search(E elem);
    void insert(E elem);
    BinaryNode<E> delete (E elem);
}
