import org.junit.jupiter.api.Test;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class AVLTest {
    // TODO: Accuracy test

    BinaryNode leftChild = new BinaryNode(10);
    BinaryNode rightChild = new BinaryNode(40);
    BinaryNode rootNode =
            new BinaryNode<Integer>(20, leftChild, rightChild, null);
    AVL emptyAVL = new AVL();

    AVL existingAVL = new AVL(rootNode);


    @Test
    public void updateTest1() {
        //TODO: Main purpose of updateTest1 is to see if the rotations work or not
        // use insert and search to alter the height & size
        Integer thirty = 30;
        Integer twenty = 29;
        System.out.println(thirty.compareTo(twenty));
        //just making sure compareTo works, and it does

        assertEquals(0, emptyAVL.height());
        assertEquals(0, emptyAVL.size());

        assertEquals(leftChild, existingAVL.search(10));

        assertEquals(1, existingAVL.height());

        emptyAVL.insert(20); //20 should be root node

        assertTrue(emptyAVL.root() == emptyAVL.search(20)); //see if it's in AVL or not

        assertEquals(0, emptyAVL.height()); //check height


        emptyAVL.insert(30); //30 is right child of root

        assertTrue(emptyAVL.root().right() == emptyAVL.search(30));
        assertEquals(1, emptyAVL.height());

        emptyAVL.insert(10); //10 is the left child of root

        assertTrue(emptyAVL.root().left() == emptyAVL.search(10));
        assertEquals(1, emptyAVL.height());

        emptyAVL.insert(9); //9 is the left child of 10

        assertTrue(emptyAVL.root().left().left() == emptyAVL.search(9));
        assertEquals(2, emptyAVL.height());

        System.out.println(emptyAVL.root().left().left());

        emptyAVL.insert(11); //11 is the right child of 10

        System.out.println(emptyAVL.root().left().right());

        assertTrue(emptyAVL.root().left().right() == emptyAVL.search(11));

        assertEquals(2, emptyAVL.height());

        System.out.println(emptyAVL.root().left());

        emptyAVL.insert(12); //12 is the right child of 11, BUT IMBALANCED

        System.out.println(emptyAVL.root().left().left());
        System.out.println(emptyAVL.root().left().right());
        System.out.println(emptyAVL.root().left().right().right());
        //checking to see if updateSize works, and how it functions with updateHeight

        assertTrue(emptyAVL.root().left().right().right() == emptyAVL.search(12));

        assertEquals(6, emptyAVL.updateSize(emptyAVL.root()));

        assertEquals(3, emptyAVL.height());


    }
}
