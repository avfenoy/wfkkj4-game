package game;
/**
 * Class Item - an item in an adventure game
 * 
 * This class is part of the "wfkkj4" application. 
 * "wfkkj4" is a simple text-based adventure game, based on the "World of Zuul" by Michael Kölling 
 * and David J. Barnes.
 * 
 * An "Item" represents an object in the game. Some of them can be picked, while others can not.
 * 
 * @author Alberto V.F.
 * @version 2015.11.18
 */
public class Item
{
    private String name;
    private boolean pickable;

    /**
     * Creates an item.
     * @param itemName The name of the item.
     * @param pickable If it can be picked or not.
     */
    public Item(String itemName, boolean pickable)
    {
        name = itemName;
        this.pickable = pickable;
    }
    
    /**
     * Get the name of the item.
     */
    public String getName()
    {
        return name;
    }    

    /**
     * @return true If the item can be picked, false if not.
     */
    public boolean isPickable()
    {
        return pickable;
    }
}
