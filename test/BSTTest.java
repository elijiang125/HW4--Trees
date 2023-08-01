import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BSTTest {

    BinaryNode leftChild = new BinaryNode(10);
    BinaryNode rightChild = new BinaryNode(40);
    BinaryNode rootNode =
            new BinaryNode<Integer>(20, leftChild, rightChild, null);
    BST emptyBST = new BST();

    BST existingBST = new BST(rootNode);

    @Test
    public void updateTest1() {
        //TODO: Main purpose of updateTest1 is to see if updateHeight & updateSize works or not
        // use insert and search to alter the height & size
        Integer thirty = 30;
        Integer twenty = 29;
        System.out.println(thirty.compareTo(twenty));
        //just making sure compareTo works, and it does

        assertEquals(0, emptyBST.height());
        assertEquals(0, emptyBST.size());

        assertEquals(leftChild, existingBST.search(10));

        assertEquals(1, existingBST.height());

        emptyBST.insert(20); //20 should be root node
        BinaryNode searchforTwenty = new BinaryNode(20);

        assertTrue(emptyBST.root() == emptyBST.search(20)); //see if it's in BST or not

        assertEquals(0, emptyBST.height()); //check height


        emptyBST.insert(30); //30 is right child of root

        assertTrue(emptyBST.root().right() == emptyBST.search(30));
        assertEquals(1, emptyBST.height());

        emptyBST.insert(10); //10 is the left child of root

        assertTrue(emptyBST.root().left() == emptyBST.search(10));
        assertEquals(1, emptyBST.height());

        emptyBST.insert(9); //10 is the left child of root

        assertTrue(emptyBST.root().left().left() == emptyBST.search(9));
        assertEquals(2, emptyBST.height());

        emptyBST.insert(11); //10 is the left child of root

        assertTrue(emptyBST.root().left().right() == emptyBST.search(11));
        assertEquals(2, emptyBST.height());

        emptyBST.insert(12); //10 is the left child of root

        assertTrue(emptyBST.root().left().right().right() == emptyBST.search(12));
        assertEquals(6, emptyBST.updateSize(emptyBST.root()));

        System.out.println(emptyBST.updateSize(emptyBST.root().left()));
        System.out.println(emptyBST.updateSize(emptyBST.root().left().left()));
        System.out.println(emptyBST.updateSize(emptyBST.root().left().right()));
        System.out.println(emptyBST.updateSize(emptyBST.root().left().right().right()));
        //checking to see if updateSize works, and how it functions with updateHeight

        assertEquals(3, emptyBST.height());

        emptyBST.insert(13);
        emptyBST.insert(29);

        assertTrue(emptyBST.root().left().right().right().right() == emptyBST.search(13));
        assertTrue(emptyBST.root().right().left() == emptyBST.search(29));
        assertEquals(8, emptyBST.updateSize(emptyBST.root()));
        assertEquals(4, emptyBST.height());

    }

    @Test
    public void updateTest2() {
        //TODO: Main purpose of updateTest2 is to check for preorder, inorder, and postorder
        // Use insert and delete to alter the list order
        // Also, make sure that the leftMost function works

        //POST ORDER TESTS

        List existingBSTpostOrder = new ArrayList<>();
        List emptyBSTpostOrder = new ArrayList<>();

        existingBSTpostOrder.add(10);
        existingBSTpostOrder.add(40);
        existingBSTpostOrder.add(20);

        emptyBSTpostOrder.add(20);



        assertTrue(existingBST.root().left() == existingBST.leftMost(existingBST.root()));
        //making sure that leftMost function works

        assertEquals(existingBSTpostOrder, existingBST.postOrderList());

        emptyBST.insert(20);
        assertEquals(emptyBSTpostOrder, emptyBST.postOrderList());
        //checking root condition

        emptyBST.insert(10);
        emptyBST.insert(30);

        emptyBSTpostOrder.remove(0);
        emptyBSTpostOrder.add(10);
        emptyBSTpostOrder.add(30);
        emptyBSTpostOrder.add(20);
        assertEquals(emptyBSTpostOrder, emptyBST.postOrderList());
        //checking child nodes condition

        emptyBSTpostOrder.remove(0);
        emptyBSTpostOrder.remove(0);
        emptyBSTpostOrder.remove(0);

        emptyBSTpostOrder.add(9);
        emptyBSTpostOrder.add(13);
        emptyBSTpostOrder.add(12);
        emptyBSTpostOrder.add(11);
        emptyBSTpostOrder.add(10);
        emptyBSTpostOrder.add(29);
        emptyBSTpostOrder.add(30);
        emptyBSTpostOrder.add(20);

        emptyBST.insert(11);
        emptyBST.insert(12);
        emptyBST.insert(13);
        emptyBST.insert(9);
        emptyBST.insert(29);

        assertEquals(emptyBSTpostOrder, emptyBST.postOrderList());
        //checking the rest regardless of height and size


        //PRE ORDER TESTS
        List emptyBSTpreOrder = new ArrayList<>();
        List existingBSTpreOrder = new ArrayList<>();

        existingBSTpreOrder.add(20);
        existingBSTpreOrder.add(10);
        existingBSTpreOrder.add(40);

        assertEquals(existingBSTpreOrder, existingBST.preOrderList());


        emptyBSTpreOrder.add(20);
        emptyBSTpreOrder.add(10);
        emptyBSTpreOrder.add(9);
        emptyBSTpreOrder.add(11);
        emptyBSTpreOrder.add(12);
        emptyBSTpreOrder.add(13);
        emptyBSTpreOrder.add(30);
        emptyBSTpreOrder.add(29);

        assertEquals(emptyBSTpreOrder, emptyBST.preOrderList());

        //INORDER TRAVERSALS
        List existingBSTinOrder = new ArrayList<>();
        List emptyBSTinOrder = new ArrayList<>();

        existingBSTinOrder.add(10);
        existingBSTinOrder.add(20);
        existingBSTinOrder.add(40);

        assertEquals(existingBSTinOrder, existingBST.inOrderList());


        emptyBSTinOrder.add(9);
        emptyBSTinOrder.add(10);
        emptyBSTinOrder.add(11);
        emptyBSTinOrder.add(12);
        emptyBSTinOrder.add(13);
        emptyBSTinOrder.add(20);
        emptyBSTinOrder.add(29);
        emptyBSTinOrder.add(30);

        assertEquals(emptyBSTinOrder, emptyBST.inOrderList());

        emptyBSTinOrder.remove(0);

        emptyBST.delete(9);
        Integer nine = 9;
        System.out.println(nine.compareTo(20));

        assertEquals(emptyBST.root().left().left(), emptyBST.delete(9));
        //assertEquals(emptyBSTinOrder, emptyBST.inOrderList());

        //struggling on delete function
    }

}
