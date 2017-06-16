package game;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Class Character - a character in an adventure game.
 * 
 * This class is part of the "wfkkj4" application. 
 * "wfkkj4" is a simple text-based adventure game, based on the "World of Zuul" by Michael Kölling 
 * and David J. Barnes.
 * 
 * A "Character" represents the player in the world of the game. The character can store items up to
 * a certain number, and if they are used, they are removed from the inventory.
 * 
 * @author Alberto V.F.
 * @version 2015.11.18
 */
public class Character
{
    private HashSet<Item> itemsCarried;      // stores the items carried by the character
    private static final int maxItems = 2;   // maximum number of items that can be carried
    private Room currentRoom;    
    
    /**
     * Create a character.
     */
    public Character()
    {
        itemsCarried = new HashSet<Item>();      
    }

    /**
     * @return A string with the items carried by the character.
     */
    public HashSet<String> getItemsCarried()
    {
        HashSet<String> itemSet = new HashSet<String>();
        for(Iterator<Item> iter = itemsCarried.iterator(); iter.hasNext(); )
        {
            itemSet.add(((Item) iter.next()).getName());
        }        
        return itemSet;     
    }
    
    /**
     * Check if character has room for a new item, and add to carried items if it has.
     * @ param The item to be picked.
     * @ return true If maximum of items permitted was not reached, false otherwise.
     */
    public boolean tryPick(Item item)
    {
        if(itemsCarried.size() < maxItems)
        {
            itemsCarried.add(item);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Try to use an item. Search if character carries the specified item, and remove from the set if so.
     * @ param itemName The item to use.
     */
    public void usedItem(String itemName)
    {
        for (Item item:itemsCarried)
        {
            if(item.getName().equals(itemName))
            {
                itemsCarried.remove(item);
                break;
            }
        }
    }
    
    public Room getRoom()
    {
        return currentRoom;
    }
    
    public void setRoom(Room room)
    {
        currentRoom = room;
    }
}
