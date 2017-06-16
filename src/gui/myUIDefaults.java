package gui;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;

/**
 * Default settings for GUI.
 * 
 * @author Alberto V.F.
 * @version 15/01/2016
 */
public class myUIDefaults
{
    public myUIDefaults()
    {
        UIManager.put("Button.background", Color.BLACK);
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("Label.background", Color.BLACK);
        
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Panel.foreground", Color.WHITE);
        UIManager.put("Label.foreground", Color.WHITE);
        
        UIManager.put("Label.font", new Font("Georgia",Font.BOLD, 13)); 
    }
}