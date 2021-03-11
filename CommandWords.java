import java.util.HashMap;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class CommandWords{

    private HashMap<String, CommandWord> validCommands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        validCommands.put("go", CommandWord.GO);
        validCommands.put("help", CommandWord.HELP);
        validCommands.put("quit", CommandWord.QUIT);
        validCommands.put("back", CommandWord.BACK);
        validCommands.put("item", CommandWord.ITEM);
        validCommands.put("take", CommandWord.TAKE);
        validCommands.put("inventory", CommandWord.INVENTORY);
        validCommands.put("drop", CommandWord.DROP);
        validCommands.put("look", CommandWord.LOOK);
        validCommands.put("eat", CommandWord.EAT);
        validCommands.put("charge", CommandWord.CHARGE);
        validCommands.put("fire", CommandWord.FIRE);
    }
    
    /**
     * Find the CommandWord associated with a command word.
     * @param commandWord The word to look up (as a string).
     * @return The CommandWord correspondng to commandWord, or UNKNOWN
     *         if it is not a valid command word.
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            return CommandWord.UNKNOWN;
        }
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll(){
        for(String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}

