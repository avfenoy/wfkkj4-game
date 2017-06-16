package game;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 
 * This class is part of the "wfkkj4" application. 
 * "wfkkj4" is a simple text-based adventure game, based on the "World of Zuul" by Michael Kölling and David J. Barnes.
 * 
 * This class starts the game, and creates and coordinates every other class during running (all characters, rooms and items, and the parser).
 * It also evaluates and executes the commands that the parser returns.
 * 
 * @author Alberto V.F.
 * @version 15/01/2016
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private HashMap<String, Item> puzzles;  // links strings (given by a command, refering to an item) to target items
    private HashMap<Item, Room> unlocks;    // links items with the rooms they unlock
    private HashSet<String> inventory;
    private Item endUnlocker;               // the item that unlocks the final room
    private int unlocks2end;                // count how many unlocks for the final room
    private Room finalRoom;                 // the room that signals the end of the game
    private String responseText;            // the text that is printed every time
    private Boolean finished;               // if true, the game is over
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        player = new Player();
        createMap();
        finished = false;
        unlocks2end = 4;
        parser = new Parser();
    }
    
    /**
     * Create all the rooms, items and puzzles, and the links between them.
     */
    private void createMap()
    {
        Room entrance, swRoom, sRoom, seRoom, wRoom, cRoom, eRoom, nwRoom, nRoom, neRoom, redRoom, blueRoom, greenRoom, yellowRoom, blackRoom;
      
        // create the rooms
        entrance = new Room("in the entrance", "There is just a door in the opposite side.", false);
        swRoom = new Room("in the south west room. It is very bright","All the light comes from the ridiculous amount of lamps hanging from the ceiling and the walls. There is also a "
             + "peculiar electric candelabrum, but the central bulb is missing. You think you are tying some things up. A red door leads to the west.", false);
        sRoom = new Room("in the south room. It is a most plain room", "The walls are grey, and it is completely empty, except for a small object stranded in the middle of the floor. "
            + "It is a lightbulb. You wonder why it is there.", false);
        seRoom = new Room("in the south east room. It is a beautiful courtyard", "Vegetation hangs from the walls, and in the center of the room, there is a statue covered in moss. The statue "
            + "is surrounded by plants, one in each direction. Actually, on the side behind the statue, where you expected to see the fourth plant, there is just an "
            + "empty flowerpot. There is a big door painted in blue to the east.", false);
        wRoom = new Room("in the west room. It is really gooey", "There are some pouffes and cushions in red and pink that seem very fluffy. A jar with a rose in the corner, and a "
            + "record player next to it. The single on it is 'heatwave' by Martha and the Vandellas, and someone put a match over it in either a profound allegory or "
            + "a failed try of a joke.", false);
        cRoom = new Room("in the central room. It is an uncanny room with a rock in its center", "There are paintings of queer creatures, half-fish and half-men, doing some kind of ritual. "
            + "In the center of the room there is a big obsidian rock with the inscription 'Ph´nglui mglw´nafh Cthulhu R´lyeh wgah´nagl fhtagn'. There are four holes "
            + "in the rock. You are confused.", false);
        eRoom = new Room("in the east room. Looks like some kind of bedroom", "You noticed it because of the big double bed on the side. There is a small table next to it with the regular "
            + "stuff: a clock, a book by some Dunsany... There is a desk with a paper with some notes about how to maintain your own garden, and a small package "
            + "of seeds.", false);
        nwRoom = new Room("in the north west room. It is very cold", "The floor is swamped by water coming from small cracks on the walls. There is a bathtub and a sink on one side. "
            + "There is a big door painted in green to the west.", false);
        nRoom = new Room("in the north room. It is really earie", "The walls are stained with blood. To the north, a huge black door stands out. There is a bar of soap on the floor, "
            + "which you find spooky. Maybe the blood comes from the countless people that slipped on it and died while solving some stupid puzzles.", false);
        neRoom = new Room("in the north east room. It is very homely","The is a round table and a fireplace. On the table, there are a lot of candles of different sizes, but none is lit. The "
            + "fireplace is not either. Even so, the room is suspiciously warm. There is a big yellow door to the east.", false);
        redRoom = new Room("in a room bathed in red light", "A ruby shines in the center.", true);
        blueRoom = new Room("in a room bathed in blue light", "A sapphire glitters in the center.", true);
        greenRoom = new Room("in a room bathed in green light", "There is a bright emerald in the center.", true);
        yellowRoom = new Room("in a room bathed in yellow light", "There is a sparkling topaz in the center.", true);
        blackRoom = new Room("in a dark room", "", true);
        
        // set exits
        entrance.setExit("north", sRoom);
        swRoom.setExit("north", wRoom);
        swRoom.setExit("east", sRoom);
        swRoom.setExit("west", redRoom);
        sRoom.setExit("north", cRoom);
        sRoom.setExit("east", seRoom);
        sRoom.setExit("south", entrance);
        sRoom.setExit("west", swRoom);
        seRoom.setExit("north", eRoom);
        seRoom.setExit("east", blueRoom);
        seRoom.setExit("west", sRoom);
        wRoom.setExit("north", nwRoom);
        wRoom.setExit("east", cRoom);
        wRoom.setExit("south", swRoom);
        cRoom.setExit("north", nRoom);
        cRoom.setExit("east", eRoom);
        cRoom.setExit("south", sRoom);
        cRoom.setExit("west", wRoom);
        eRoom.setExit("north", neRoom);
        eRoom.setExit("south", seRoom);
        eRoom.setExit("west", cRoom);
        nwRoom.setExit("east", nRoom);
        nwRoom.setExit("south", wRoom);
        nwRoom.setExit("west", greenRoom);
        nRoom.setExit("north", blackRoom);
        nRoom.setExit("east", neRoom);
        nRoom.setExit("south", cRoom);
        nRoom.setExit("west", nwRoom);
        neRoom.setExit("east", yellowRoom);
        neRoom.setExit("south", eRoom);
        neRoom.setExit("west", nRoom);
        redRoom.setExit("east", swRoom);
        blueRoom.setExit("west", seRoom);
        greenRoom.setExit("east", nwRoom);
        yellowRoom.setExit("west", neRoom);
        blackRoom.setExit("south", nRoom);
        
        // add items
        Item match, fireplace, lightbulb, lamp, seeds, flowerpot, soap, bathtub, ruby, sapphire, emerald, topaz, hole1, hole2, hole3, hole4, rock;
        
        match = new Item("match",true);
        fireplace = new Item("fireplace",false);
        lightbulb = new Item("lightbulb", true);
        lamp = new Item("lamp", false);
        seeds = new Item("seeds", true);
        flowerpot = new Item("flowerpot", false);
        soap = new Item("soap", true);
        bathtub = new Item("bathtub", false);
        sapphire = new Item("sapphire", true);
        emerald = new Item("emerald", true);
        ruby = new Item("ruby", true);                
        topaz = new Item("topaz", true);
        hole1 = new Item("hole1", false);
        hole2 = new Item("hole2", false);
        hole3 = new Item("hole3", false);
        hole4 = new Item("hole4", false);
        rock = new Item("rock", false);
        
        // locate items
        swRoom.addItem(lamp);
        sRoom.addItem(lightbulb);
        seRoom.addItem(flowerpot);
        wRoom.addItem(match);
        eRoom.addItem(seeds);
        nwRoom.addItem(bathtub);
        nRoom.addItem(soap);
        neRoom.addItem(fireplace);
        redRoom.addItem(ruby);
        blueRoom.addItem(sapphire);
        greenRoom.addItem(emerald);
        yellowRoom.addItem(topaz);
        cRoom.addItem(hole1);
        cRoom.addItem(hole2);
        cRoom.addItem(hole3);
        cRoom.addItem(hole4);
        cRoom.addItem(rock);
        
        // set puzzle
        puzzles = new HashMap<String, Item>();
        puzzles.put(match.getName(), fireplace);
        puzzles.put(lightbulb.getName(), lamp);
        puzzles.put(seeds.getName(), flowerpot);
        puzzles.put(soap.getName(), bathtub);
        puzzles.put(ruby.getName(), hole1);
        puzzles.put(sapphire.getName(), hole2);
        puzzles.put(emerald.getName(), hole3);
        puzzles.put(topaz.getName(), hole4);
        
        // map unlocks
        unlocks = new HashMap<Item, Room>();
        unlocks.put(fireplace,redRoom);
        unlocks.put(lamp,yellowRoom);
        unlocks.put(flowerpot,greenRoom);
        unlocks.put(bathtub,blueRoom);
        unlocks.put(rock, blackRoom);

        Character character = new Character();
        character.setRoom(entrance);  // start game in the entrance
        player.setCharacter(character); // assign character to player
        inventory = new HashSet<String>();
        finalRoom = blackRoom;
        
        endUnlocker = rock;
        responseText = new String(welcomeString());
    }
    
    /**
     *  Play routine.  Loops until end of play.
     */
    public void play() 
    {
        // Enter the main command loop.  Here we repeatedly read commands and execute them until the game is over.               
        while (! finished) 
        {
            @SuppressWarnings("unused")
			Command command = parser.getCommand();
            if(player.getCharacter().getRoom().equals(finalRoom))
            {
                finishGame();
                responseText = launchEnding();
            }
        }
    }
    
    /**
     *  Accessor method for field player.
     *  @return the Player stored.
     */
    public Player getPlayer()
    {
        return player;
    }


    /**
     *  Accessor method for the response text.
     *  @return the response text.
     */
    public String getResponse()
    {
        return responseText;
    }
    
    /**
     * Mutator method for the response text.
     *  @return the Player stored.
     */
    private void setResponse(String newResponse)
    {
        responseText = newResponse;
    }
    
    /**
     * Create the opening message for the player.
     * @return the string with the message.
     */
    private String welcomeString()
    {
        String welcome = "wfkkj4@½#j2~&·$%=4%P$ŋđð.... you suddenly awake.\n" +
        "You don't remember anything. Barely. Last days were quite frantic.\n" +
        "You never were very prone to magical thinking, but lately you've had the feeling that something strange was going on.\n" +
        "And here you are. In front of an odd looking palace, with no clue about how you got there.\n" +
        "Even so, you feel that something lures you from within. You walk inside.\n\n" +
        player.getCharacter().getRoom().getLongDescription();
        return welcome;
    }
 
    /**
     * Create the ending message.
     * @return the string with the message.
     */

    private String launchEnding()
    {
        String ending = "Your heart is pounding faster and faster. Something is moving in the dark.....\n" +
        "All of a sudden, a small kitty emerges from the shadows. It is the cutest thing you have ever seen in your life.\n" +
        "You can't handle so much cuteness, and start to burst into tears as you behold this little gift from God in all its glory.\n" +
        "You don't care about what was going on around anymore.\n" + "For you truly have WON.";
        return ending;
    }
   
    /**
     *  Processes a command given in the form of a string.
     *  
     *  @param commandString A string with the command to be processed.
     *  @return true If the command was succesful, false otherwise.
     *  
     */
    public boolean processCommandString(String commandString)
    {
        String word1 = null;
        String word2 = null;
        
        Scanner tokenizer = new Scanner(commandString);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                // note: we just ignore the rest of the input line.
            }
        }
        tokenizer.close();
        
        Command command = new Command(new CommandWords().getCommandWord(word1), word2);
        boolean succesful = processCommand(command);
        
        if(player.getCharacter().getRoom().equals(finalRoom))
        {
            finishGame();
            responseText = launchEnding();
        }
        return succesful;
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true if the command was succesful, false otherwise.
       */
    private boolean processCommand(Command command) 
    {
        boolean commandSuccessful = false;
        
        CommandWord commandWord = command.getCommandWord();

        switch (commandWord)
        {
            case UNKNOWN:
                System.out.println();
                System.out.println("I don't know what you mean...");
                System.out.println();
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case PICK:
                commandSuccessful = pickItem(command);
                break;
                
            case USE:
                commandSuccessful = useItem(command);
                break;
                
            case LOOK:
                look();
                break;
                
            case INVENTORY:
                showInventory();
                break;
                
            case MAP:
                printMap();
                break;

            case QUIT:
                quit(command);
                break;
        }
        
        return commandSuccessful;
    }

    // implementations of user commands:

    /**
     * Print out the commands that the player can use.
     */
    private void printHelp() 
    {
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println();
    }

    /** 
     * Try to go in one direction. If there is an exit and it is not locked, enter the new room, otherwise print an error message.
     * @param command The command specifying the action.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            setResponse("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = player.getCharacter().getRoom().getExit(direction);    // Try to leave current room.

        if (nextRoom == null)
        {
            System.out.println();
            System.out.println("There is no door.");
            System.out.println();
        }
        else if(nextRoom.getState())  // check if room is locked
        {
            setResponse("The door is locked!");           
        }
        else 
        {
            player.getCharacter().setRoom(nextRoom);
            setResponse(player.getCharacter().getRoom().getLongDescription());
        }
    }

    /**
     * Try to pick an item from the room. If it is a valid item, the character tries to pick it, otherwise print an error message.
     * @param command The command specifying the action.
     * @return true If picking was succesful
     */
    private boolean pickItem(Command command)
    {
        boolean success = false;
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pick...
            setResponse("Pick what?");           
        }
        
        String item2pickString = command.getSecondWord();
        Item item2pick = player.getCharacter().getRoom().getItem(item2pickString); // try to get specified item from the room
        
        if(item2pick != null)
        {
            if(item2pick.isPickable())
            {
                if (player.getCharacter().tryPick(item2pick)) // if character succesfully picks the item
                {
                    success = true;
                    player.getCharacter().getRoom().removeItem(item2pick); // remove item from room
                    player.getCharacter().getRoom().roomCleared();
                    look();
                    
                    inventory = player.getCharacter().getItemsCarried();
                }        
                else
                {
                    setResponse("You are carrying too many things already!");
                }
            }
            else 
            {
                setResponse("It can't be picked.");
            }
        }
        else
        {
            setResponse("You try to pick the " + item2pickString + ". Your action seems pointless.");
        }
        return success;
    }
    
    /**
     * Try to use an item. If the room contains the specific item that is targeted by the item used, trigger effect. Otherwise, print error message.
     * @param command The command specifying the action.
     * @return true If using was succesful.
     */
    private boolean useItem(Command command)
    {
        boolean success = false;
        if(!command.hasSecondWord())
        {
            setResponse("Use what?");
        }
        
        String item2use = command.getSecondWord();
        
        if(player.getCharacter().getItemsCarried().contains(item2use))
        {
            if(player.getCharacter().getRoom().getItems().contains(puzzles.get(item2use))) // if the room contains target item
            {
                setResponse("You used the " + item2use + ".");
                player.getCharacter().usedItem(item2use); // remove item from character's inventory
                success = true;
                
                if(!player.getCharacter().getRoom().getItems().contains(endUnlocker)) // for standard rooms
                {
                    unlocks.get(puzzles.get(item2use)).openDoor();
                    player.getCharacter().getRoom().roomCleared();
                    look();
                    setResponse("You hear a noise in the distance.");
                }                
                else if(player.getCharacter().getRoom().getItems().contains(endUnlocker) && unlocks2end == 1) // for the room with the item that unlocks the final room, unlock room only if it is the final unlock
                {
                    unlocks.get(endUnlocker).openDoor();
                    player.getCharacter().getRoom().roomCleared();
                    look();
                    unlocks2end--;
                    
                    setResponse("You hear a loud noise in the distance. Sounds like a big door opening.");
                }                
                else
                {
                    unlocks2end --; 
                }
            }            
            else
            {
                setResponse("Nothing happened.");  
            }
        }
        else
        {
            setResponse("You don't have a " + item2use + ".");
        }     
        return success;
    }
    
    /**
     * Print out a description of the room depending on whether it has been cleared or not.
     */
    private void look()
    {
        if(!player.getCharacter().getRoom().isCleared())
        {
            setResponse(player.getCharacter().getRoom().getLongDescription() + player.getCharacter().getRoom().getExtendedDescription());
        }
        else
        {
            setResponse(player.getCharacter().getRoom().getLongDescription());
        }
    }
    
    /**
     * Print items carried by the character.
     */
    private void showInventory()
    {
        System.out.println();
        System.out.println(player.getCharacter().getItemsCarried());
        System.out.println();
    }
    
    /**
     * Print out a map of the rooms to help player navigate through the game.
     */
    public void printMap()
    {
        System.out.println("                  ______");
        System.out.println("                 |      |");
        System.out.println("    _____________|______|_____________");
        System.out.println("   |      |      |      |      |      |");
        System.out.println("   |______|______|______|______|______|");
        System.out.println("          |      |      |      |");
        System.out.println("    ______|______|______|______|______");
        System.out.println("   |      |      |      |      |      |");
        System.out.println("   |______|______|______|______|______|");
        System.out.println("                 |      |");
        System.out.println("                 |______|");
        System.out.println();
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see whether we really quit the game.
     * @return true If this command quits the game, false otherwise.
     */
    private void quit(Command command) 
    {
        if(command.hasSecondWord()) 
        {
            setResponse("Quit what?");
        }
        else 
        {
            finishGame();  // signal that we want to quit
        }
    }
    
    /**
     * Accessor for the inventory.
     * return the inventory.
     */
    public HashSet<String> getInventory()
    {
        return inventory;
    }
    
    /**
     * Changes the state of the game to finished.
     */
    public void finishGame()
    {
        finished = true;
    }
    
    /**
     * Accessor for finished field.
     * return finished.
     */
    public boolean getFinished()
    {
        return finished;
    }
}