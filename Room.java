import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.List;
import java.util.HashSet;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;

    public Room(String description){
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<Item>();
    }

    public void setExit(String direction, Room neighbor){
        exits.put(direction, neighbor);
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void removeItem(Item item){
        items.remove(item);
    }
    
    public Item getItem(String name){
        for(Item item : items){
            if(item.getName().equals(name)){
                return item;
            }
        }
        
        // if we get to this point no item with this name exists.
        return null;
    }
 
    public String getShortDescription(){
        return description;
    }
    
    public ArrayList<Item> returnList(){
        return items;
    }
    
    public String getItemName(){
        String returnString = "Items: ";
        for(Item item : items) {
            returnString += " " + item.getName();
        }
        return returnString;
    }
    
     public int getItemWeight(Item item){
        int returnWeight = 0;
        returnWeight += item.getWeight();
        return returnWeight;
    }

    public String getItemString(){
        String returnString = "";
        for(Item item : items) {
            returnString += "" + item.getItemDescription();
        }
        return returnString;
    }

    public String getLongDescription(){
        return "You are " + description + ".\n" + getExitString() + ".\n" + getItemName();
    }

    private String getExitString(){
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Integer getItemsSize(){
        return items.size();
    }

    public Room getExit(String direction){
        return exits.get(direction);
    }
}

