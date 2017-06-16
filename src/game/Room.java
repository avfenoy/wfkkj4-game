package game;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "wfkkj4" application. 
 * "wfkkj4" is a simple text-based adventure game, based on the "World of Zuul" by Michael Kölling 
 * and David J. Barnes.
 *
 * A "Room" represents one location in the scenery of the game. It is connected to other rooms
 * via exits. For each existing exit, the room stores a reference to the neighboring room. It
 * also stores items. The room can be locked or not, and in case it is locked it can be unlocked.
 * 
 * @author Alberto V.F.
 * @version 2015.11.18
 */

public class Room 
{
    private String description;
    private String extendedDescription;
    private boolean locked;                     // if the room is locked or not
    private HashMap<String, Room> exits;        // stores exits of this room
    private HashSet<Item> items;                // stores items in this room
    private boolean cleared;                    // true if the room is completed


    /**
     * Create a room described "Initially, it has no exits. "description" is something like 
     * "a kitchen" or "an open court yard". "extendedDescription" is a detailed description
     * about the room appearence and what is in it.
     * @param description The room's description.
     * @param extendedDescription More information about the room.
     * @param locked If the room is locked or not
     */
    public Room(String description, String extendedDescription, boolean locked) 
    {
        this.description = description;
        this.extendedDescription = extendedDescription;
        this.locked = locked;
        exits = new HashMap<String, Room>();
        items = new HashSet<Item>();
        cleared = false;
    }
    
    /**
     * Define an exit for this room.
     * @param neighbours  The rooms to which the exit leads.
     */
 
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     * "You are in the kitchen.
     *     
     *  Exits: north west"
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n \n";
    }
    
    /**
     * @return The extended description defined in the constructor.
     */
    public String getExtendedDescription()
    {
        return extendedDescription;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west". Currently unused.
     * @return Details of the room's exits.
     */
//    private String getExitString()
//    {
//        String returnString = "Exits:";
//        Set<String> keys = exits.keySet();
//        for(String exit : keys)
//        {
//            returnString += " " + exit;
//        }
//        return returnString;
//    }
    
    public Set<String> getExits()
    {
        Set<String> roomExits = exits.keySet();
        return roomExits;
    }
    
    /**
     * Return the room that is reached if we go from this room in direction "direction". If there 
     * is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * @return true If the room is locked, false otherwise.
     */
    public boolean getState()
    {
        return locked;
    }
    
    /**
     * Add one item to the room.
     * @param item The item to be added.
     */
    public void addItem(Item item)
    {
        items.add(item);
    }
    
    /**
     * Remove one item from the room.
     * @param item The item to be removed.
     */
    public void removeItem(Item item)
    {
        items.remove(item);
    }
    
    /**
     * @return The set of items in the room.
     */
    public HashSet<Item> getItems()
    {
        return items;
    }
    
    /**
     * Retrieve an item with the specified name.
     * @param itemString The name of the item to be retrieved.
     * @return The item
     */
    public Item getItem(String itemString)
    {
        Item item2get = null;
        for (Item item:items)
        {
            if(item.getName().equals(itemString))
            {
                item2get = item;
                break;
            }
        }
        return item2get;
    }

    /**
     * Unlocks the room.
     */
    public void openDoor()
    {
        locked = false;
    }
    
    public boolean isCleared()
    {
        return cleared;
    }
    
    public void roomCleared()
    {
        cleared = true;
    }
}
