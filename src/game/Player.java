package game;

/**
 * This class represents the player in an adventure game.
 * 
 * @author Alberto V.F.
 * @version 15/01/2016
 */
public class Player
{
    // instance variables - replace the example below with your own
    private Character character;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
    }
    
    public Character getCharacter()
    {
        return character;
    }
    
    public void setCharacter(Character character)
    {
        this.character = character;
    }
    
}
