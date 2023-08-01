import java.util.*;

public class AVL<E extends Comparable<E>> implements Tree<E>{

    private int height;
    private int size;
    private BinaryNode<E> root;
    private int RRotations; // this will be used to see if the amount of rotations was correct
    private int LRotations; // this will be used to see if the amount of rotations was correct

    public AVL(){
        this.root = null;
        this.height = 0;
        this.size = 0;
        this.RRotations = 0;
        this.LRotations = 0;
    }

    public AVL(BinaryNode<E> root){
        this.root = root;
        this.height = 0;
        updateHeight();
        this.size = updateSize(this.root);
        this.RRotations = 0;
        this.LRotations = 0;
    }

    // Access fields
    public int getRRotations(){
        return this.RRotations;
    }
    public int getLRotations(){
        return this.LRotations;
    }
    public BinaryNode<E> root() {
        return this.root;
    }
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
            return 1 + updateSize(node.left()) + updateSize(node.right());
        }
        else if (node.hasLeft()) {
            return 1 + updateSize(node.left());
        }
        else if (node.hasRight()) {
            return 1 + updateSize(node.right());
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

    //helper function to find height of left and right trees
    public int updateTempHeight(BinaryNode<E> node) {
        //if root is null OR only root exists, then height is 0
        if (node == null || (!node.hasLeft() && !node.hasRight())) {
            return 0;
        }
        else {
            BinaryNode<E> left = node.left();
            BinaryNode<E> right = node.right();
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
                return leftHeight;
            }
            else if (leftHeight < rightHeight) {
                return rightHeight;
            }
            else {
                return leftHeight;
            }

        }
    }

    // Traversals that return lists
    //are these traversals the same as
    // TODO: Preorder traversal
    public List<E> preOrderList() {
        List<E> order = new ArrayList<E>();

        if (this.root == null) { //base case where root node is null
            return Collections.emptyList(); //return empty list
        }

        Stack<BinaryNode> BNStack = new Stack<BinaryNode>();
        BNStack.push(this.root);

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


    /*
    TODO: rotateRight
     *              x                          y
     *            /   \                      /   \
     *           y     C     ===>           A     x
     *         /   \                             /  \
     *        A    B                            B    C
     * You should never rotateRight if the left subtree is empty.
     * Make sure you increment the RRotations.
    */
    public void rotateRight(BinaryNode<E> node){

        BinaryNode<E> newRoot = node.left();
        BinaryNode<E> newRight = node;
        BinaryNode<E> leftoverRight = node.left().right();

        //this is the new root
        node.setData(newRoot.data());
        node.setLeft(newRoot.left());
        node.setRight(newRight); //original root node is here
        node.setParent(null);

        //because new root's original right nodes can't be there, will be left child of new right node
        newRight.setLeft(leftoverRight);

        RRotations++; //increments

    }

    /*
     TODO: rotateLeft
      *              x                          y
      *            /   \                      /   \
      *           y     C     <==           A     x
      *         /   \                             /  \
      *        A    B                            B    C
      * You should never rotateLeft if the right subtree is empty.
      * Make sure you increment the LRotations.
      * Symmetrical to above.
     */
    public void rotateLeft(BinaryNode<E> node){
        BinaryNode<E> newRoot = node.right();
        BinaryNode<E> newLeft = node;
        BinaryNode<E> leftoverLeft = node.right().left();

        //this is the new root
        node.setData(newRoot.data());
        node.setLeft(newLeft);
        node.setRight(newRoot.right()); //original root node is here
        node.setParent(null);

        //because new root's original right nodes can't be there, will be right child of new left node
        newLeft.setRight(leftoverLeft);

        LRotations++; //increments
    }

    /*
     TODO: possibleRotateRight
      * If the current node is unbalanced with the right tree height being smaller
      * than the left subtree height, rotate right. Otherwise, don't do anything.
    */
    public void possibleRotateRight(BinaryNode<E> node){

        rotateRight(node);
    }

    /*
     TODO: possibleRotateLeft
      * If the current node is unbalanced with the left tree height being smaller
      * than the right subtree height, rotate left. Otherwise, don't do anything.
    */
    public void possibleRotateLeft(BinaryNode<E> node){

        rotateLeft(node);
    }

    /*
     TODO: mkBalanced
      * Given a node, balance it if the heights are unbalanced.
      * Hint: rotations!!!
    */
    public void mkBalanced(BinaryNode<E> node){
        //if left side is heavy, then rotateright
        if (updateSize(node.left()) > updateSize(node.right()) &&
                updateTempHeight(node.left()) - updateTempHeight(node.right()) > 1) {
            possibleRotateRight(node);
        }
        //if right side is heavy, rotateleft
        else if (updateSize(node.left()) < updateSize(node.right()) &&
                updateTempHeight(node.right()) - updateTempHeight(node.left()) > 1) {
            possibleRotateRight(node);
        }
    }


    // Helpers for BST/AVL methods
    // TODO: extractRightMost - identical to BST
    public BinaryNode<E> extractRightMost(BinaryNode<E> curNode) {
        if (curNode.getRight() != null) {
            extractRightMost(curNode.right());
        }
        return curNode;
    }

    // AVL & BST Search & insert same
    // TODO: search - identical to BST
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

    /*
     TODO: insert - slightly different from BST but similar logic
      * Hint: mkBalanced will be your best friend here.
    */
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
            mkBalanced(this.root);
            this.size++;
        }

        else if (curNode.left() == null && elem.compareTo(curNode.data()) < 0) {
            //left node empty and data less than element
            mkBalanced(this.root);
            newNode.setData(elem);
            newNode.setParent(curNode);
            curNode.setLeft(newNode);
            updateHeight();
            this.size++;
        }

        else if (curNode.right() == null && elem.compareTo(curNode.data()) > 0) {
            //right node empty and data greater than element
            mkBalanced(this.root);
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


    /*
     TODO: delete - slightly different from BST but similar logic
      * Hint: mkBalanced will be your best friend here.
    */
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
            currentNode.setParent(new BinaryNode<E>(
                    currentNode.parent().data(),
                    currentNode.parent().parent(),
                    null,  //gets rid of curNode
                    currentNode.parent().right()));  //updates parent node
        }

        //if item matches data, AND was right child of parent
        else if (currentNode.data() == elem && currentNode.parent().right() == currentNode) {
            currentNode.setParent(new BinaryNode<E>(
                    currentNode.parent().data(),
                    currentNode.parent().parent(),
                    currentNode.parent().left(),  //gets rid of curNode
                    null));  //updates parent node
        }

        else if (elem.compareTo(currentNode.data()) < 0) { //if less than, recursion
            delete(elem, currentNode.left());
        }
        else if (elem.compareTo(currentNode.data()) > 0) { //if more than, recursion
            delete(elem, currentNode.right());
        }

        return currentNode;
    }


    // Stuff to help you debug if you want
    // Can ignore or use to see if it works.
    static <E extends Comparable<E>> Tree<E> mkAVL (Collection<E> elems) {
        Tree<E> result = new AVL<>();
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

}
