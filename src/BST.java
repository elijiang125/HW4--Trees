
import java.util.*;

public class BST<E extends Comparable<E>> implements Tree<E> {

    private int height;
    private int size;
    private BinaryNode<E> root;

    public BST(){
        this.root = null;
        this.height = 0;
        this.size = 0;
    }

    // TODO: BST
    public BST(BinaryNode<E> root){
        this.root = root;
        this.height = 0;

        updateHeight(); //gets the height
        this.size = updateSize(this.root);
    }

    // Access field
    public BinaryNode<E> root() {
        return this.root;
    }

    // Basic properties
    public int height() {
        return this.height;
    }
    public int size() {
        return this.size;
    }
    public boolean isBalanced() {
        return root.isBalanced();
    }

    //helper function to update size
    public int updateSize(BinaryNode<E> node) {

        if (node == null) {
            return 0;
        }
        else if (node.hasLeft() && node.hasRight()) {
            return (1 + updateSize(node.left()) + updateSize(node.right()));
        }
        else if (node.hasLeft()) {
            return (1 + updateSize(node.left()));
        }
        else if (node.hasRight()) {
            return (1 + updateSize(node.right()));
        }
        else {
            return 1;
        }
    }

    // TODO: updateHeight - Update the root height to reflect any changes
    public void updateHeight() {
        //if root is null OR only root exists, then height is 0
        if (this.root == null || (!this.root.hasLeft() && !this.root.hasRight())) {
            this.height = 0;
        }
        else {
            BinaryNode<E> left = this.root.left();
            BinaryNode<E> right = this.root.right();
            int leftHeight = 1;
            int rightHeight = 1;

            while (left != null && right != null) {
                if (left.hasLeft() || left.hasRight()) {
                    if (updateSize(left.left()) >= updateSize(left.right())) {
                        //Rely on updateSize to see if left tree is heavier
                        left = left.left();
                    }
                    else if (updateSize(left.left()) <= updateSize(left.right())) {
                        //Rely on updateSize to see if right tree is heavier
                        left = left.right();
                    }
                    leftHeight++;

                }
                //same thing applies for right node of root tree
                if (right.hasLeft() || right.hasRight()) {
                    if (updateSize(right.left()) >= updateSize(right.right())) {
                        right = right.left();
                    }
                    else if (updateSize(right.left()) <= updateSize(right.right())) {
                        right = right.right();
                    }
                    rightHeight++;

                }
                if ((!right.hasLeft() && !right.hasRight()) && (!left.hasLeft() && !left.hasRight())) {
                    //if left AND right node have no children
                    left = null;
                    right = null;
                }
            }
            if (leftHeight > rightHeight) {
                this.height = leftHeight;
            }
            else if (leftHeight < rightHeight) {
                this.height = rightHeight;
            }
            else {
                this.height = leftHeight;
            }

        }
    }

    // Traversals that return lists
    // TODO: Preorder traversal
    public List<E> preOrderList() {
        List<E> order = new ArrayList<E>();

        if (this.root == null) { //base case where root node is null
            return Collections.emptyList(); //return empty list
        }

        Stack<BinaryNode> BNStack = new Stack<BinaryNode>();
        BNStack.push(this.root);

        //Okay so I'm assuming this is how preorder traversals work
        //1. print parent node
        //2. print left node
        //3. print right node

        while (!BNStack.isEmpty()) {
            BinaryNode<E> currNode = BNStack.peek();
            order.add(currNode.data()); //parent node added
            BNStack.pop(); //pops out parent node, but we saved it using currNode

            if (currNode.right() != null) {
                BNStack.push(currNode.right());
            }
            //Note that right child is pushed first in order to print the left child first

            if(currNode.left() != null) {
                BNStack.push(currNode.left());
            }
        }
        return order;
    }

    // TODO: Inorder traversal
    public List<E> inOrderList() {
        //What I believe how inorder works is this:

        //1. traverse all the way to the leftmost subtree, and print
        //2. find the first unused root, and print
        //3. find its right node, and repeat 1-3

        List<E> order = new ArrayList<E>();

        if (this.root == null) {
            return Collections.emptyList(); //return empty list
        }

        Stack<BinaryNode> BNStack = new Stack<BinaryNode>();
        BinaryNode<E> start = this.root;

        while (start != null || BNStack.size() > 0) { //in case the node never ends
            while(start != null) {
                BNStack.push(start);
                start = start.left();
            }

            start = BNStack.pop(); //current must be null at this point

            order.add(start.data());

            start = start.right(); //proceed to right node

        }
        return order;
    }

    //serving as a helper function for inorder traversal
    public BinaryNode<E> leftMost(BinaryNode<E> tree) {
        BinaryNode<E> start = tree;
        while(start.left() != null) {
            //find leftmost subtree
            start = start.left();
        }
        return start;
    }

    // TODO: Postorder traversal
    public List<E> postOrderList() {
        List<E> order = new ArrayList<>();

        Stack<BinaryNode> parentNodes = new Stack<BinaryNode>();
        Stack<BinaryNode> childrenNodes = new Stack<BinaryNode>();

        if (this.root == null) {
            return Collections.emptyList(); //return an empty list
        }

        //push root to the parentNode stack
        parentNodes.push(this.root);

        //run while first stack isn't empty
        while (!parentNodes.isEmpty()) {
            //pop the parent nodes to push the child nodes into the childrenNodes stack
            BinaryNode<E> parent = parentNodes.pop();
            childrenNodes.push(parent);

            //push back to parent nodes
            if (parent.left() != null) {
                parentNodes.push(parent.left());
            }
            if (parent.right() != null) {
                parentNodes.push(parent.right());
            }
        }

        //add all elements from the second stack to the list
        while (!childrenNodes.isEmpty()) {
            BinaryNode<E> temp = childrenNodes.pop();
            order.add(temp.data());
        }

        return order;
    }

    // Helpers for BST/AVL methods
    // TODO: extractRightMost
    //    This will be called on the left subtree and will get the maximum value.
    public BinaryNode<E> extractRightMost(BinaryNode<E> curNode) {
        if (curNode.getRight() != null) {
            extractRightMost(curNode.right());
        }
        return curNode;
    }

    // AVL & BST Search & insert same
    // TODO: search
    public BinaryNode<E> search(E elem) {
        BinaryNode<E> start = this.root;


       while (start != null) {
           if (elem == null) {
               return null;
           }
           if (elem == start.data()) {
               //if node found, return the node
               return start;
           } else if (start.right() == null && start.left() == null && elem != start.data()) {
               //if node not found, return null
               return null;
           } else if (elem.compareTo(start.data()) < 0) { //if elem is less than
               start = start.left();
           } else if (elem.compareTo(start.data()) > 0) {
               start = start.right();
           }
       }
       return null;

    }

    // TODO: insert
    // need help, maybe get a helper function with recursion?
    public void insert(E elem) {

        insert(elem, this.root);
    }

    public void insert(E elem, BinaryNode<E> curNode) {
        BinaryNode<E> newNode = new BinaryNode<E>(elem);

        if(this.root == null) {
            this.root = new BinaryNode<E>(elem, null, null, null);
        }

        else if (curNode == null) { //if empty
            curNode = new BinaryNode<E>(elem, null, null, null);
            this.size++;
        }

        else if (curNode.getLeft() == null && elem.compareTo(curNode.data()) < 0) {
            //left node empty and data less than element
            newNode.setData(elem);
            newNode.setParent(curNode);
            curNode.setLeft(newNode);
            updateHeight();
            this.size++;
        }

        else if (curNode.getRight() == null && elem.compareTo(curNode.data()) > 0) {
            //right node empty and data greater than element
            newNode.setData(elem);
            newNode.setParent(curNode);
            curNode.setRight(newNode);
            updateHeight();
            this.size++;
        }
        else if (elem.compareTo(curNode.data()) < 0) {
            insert(elem, curNode.left());
        }
        else if (elem.compareTo(curNode.data()) > 0) {
            insert(elem, curNode.right());
        }
    }

    // TODO: delete
    public BinaryNode<E> delete(E elem) {
        return delete(elem, this.root);
    }

    public BinaryNode<E> delete(E elem, BinaryNode<E> currentNode) {
        if (currentNode == null) { //if tree is empty, return null
            return null;
        }
        if (currentNode.data() == elem && currentNode.getLeft() != null && currentNode.getRight() != null) {
            //if item matches, but the node removed has two children
            currentNode.setData(currentNode.right().data());
            //max element replaces deleted item
            currentNode.setRight(currentNode.right().right());
            //max node's right element
        }
        //if the item matches the data, and was left child of parent
        else if (currentNode.data() == elem && currentNode.parent().left() == currentNode) {
            currentNode.setParent(new BinaryNode<E>(currentNode.parent().data(),
                    null, //currentNode is gone
                    currentNode.parent().right(),
                    currentNode.parent().parent()));  //updates parent node
        }

        //if item matches data, AND was right child of parent
        else if (currentNode.data() == elem && currentNode.parent().right() == currentNode) {
            currentNode.setParent(new BinaryNode<E>(
                    currentNode.parent().data(),
                    currentNode.parent().left(),
                    null,  //gets rid of currentNode
                    currentNode.parent().parent()));  //updates parent node
        }

        else if (elem.compareTo(currentNode.data()) < 0) { //if less than, recursion
            return delete(elem, currentNode.left());
        }
        else if (elem.compareTo(currentNode.data()) > 0) { //if more than, recursion
            return delete(elem, currentNode.right());
        }
        return currentNode;

    }

    // Stuff to help you debug if you want
    // Can ignore or use to see if it works.
    static <E extends Comparable<E>> Tree<E> mkBST (Collection<E> elems) {
        Tree<E> result = new BST<>();
        for (E e : elems) result.insert(e);
        return result;
    }
    public TreePrinter.PrintableNode getLeft() {
        return this.root.hasLeft() ? this.root.left() : null;
    }
    public TreePrinter.PrintableNode getRight() {
        return this.root.hasRight() ? this.root.right() : null;
    }
    public String getText() {
        return (this.root != null) ? this.root.getText() : "";
    }
    public static void main(String[]args) {

    }
}
