package game;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A class for testing some Character methods.
 *
 * @author Alberto V.F. 
 * @version 15/01/2016
 */
public class CharacterTest
{
    /**
     * Default constructor for test class CharacterTest
     */
    public CharacterTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void testTryPick()
    {
        Character character = new Character();
        Item item1 = new Item("item1", true);
        Item item2 = new Item("item2", true);
        Item item3 = new Item("item3", true);
        assertEquals(true, character.tryPick(item1));
        assertEquals(1, character.getItemsCarried().size());
        assertEquals(true, character.tryPick(item2));
        assertEquals(2, character.getItemsCarried().size());
        assertEquals(false, character.tryPick(item3));
        assertEquals(2, character.getItemsCarried().size());
    }
    
    @Test
    public void testUsedItem()
    {
        Character character = new Character();
        Item item1 = new Item("item1", true);
        @SuppressWarnings("unused")
		Item item2 = new Item("item2", true);
        character.tryPick(item1);
        assertEquals(1, character.getItemsCarried().size());
        character.usedItem("item2");
        assertEquals(1, character.getItemsCarried().size());
        character.usedItem("item1");
        assertEquals(0, character.getItemsCarried().size());
    }    
}
