import java.util.ArrayList;
import java.util.Random;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game{
    private Parser parser;
    private Player player;
    private boolean wantToQuit;
    private Room roomDestination;
    private Room transporter;
    private ArrayList<Room> rooms;
    public Game(){
        
        parser = new Parser();
        wantToQuit = false;
        roomDestination = null;
        rooms = new ArrayList<>();
        createRooms();
    }

    private void createRooms(){
        Room outside, theater, pub, lab, office, transporter;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transporter = new Room("random room incomming");
        
        rooms.add(outside);
        rooms.add(theater);
        rooms.add(pub);
        rooms.add(lab);
        rooms.add(office);
       

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);
        theater.setExit("east", transporter);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        outside.addItem(new Item("Saw", "broken chainsaw", 100));
        outside.addItem(new Item("Hammer", "rusty hammer", 15));

        lab.addItem(new Item("Rock", "big rock", 400));
        lab.addItem(new Item("Magic-Cookie", "magical cookie", 1));
        
        theater.addItem(new Item("Key", "the one Key to rule them all", 1));
        
        office.addItem(new Item("Beamer", "can you handle this?", 10));

        // start game outside
        player = new Player("Hermann", outside);
    }

    public void play(){            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(player.getRoom().getLongDescription());
    }

    private boolean processCommand(Command command) 
    {
        wantToQuit = false;
        
        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN){
            System.out.println("I don't know what you mean...");
            return false;
        }else if (commandWord == CommandWord.HELP){
            printHelp();
        }
        else if (commandWord == CommandWord.GO){
            goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT){
            wantToQuit = quit(command);
        }
        else if (commandWord == CommandWord.BACK){
            player.goBack();
        }
        else if (commandWord == CommandWord.ITEM){
            getItemInfo(command);
        }
        else if (commandWord == CommandWord.TAKE){
            take(command);
        }
        else if (commandWord == CommandWord.DROP){
            drop(command);
        }
        else if (commandWord == CommandWord.INVENTORY){
            player.showInventory();
        }
        else if (commandWord == CommandWord.LOOK){
            look();
        }
        else if (commandWord == CommandWord.EAT){
            eatCookie(command);
        }
        else if (commandWord == CommandWord.CHARGE){
            charge();
        }
        else if (commandWord == CommandWord.FIRE){
            fire();
        }
        

        // else command not recognised.
        return wantToQuit;
    }

    private void printHelp(){
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command){
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = player.getRoom().getExit(direction);

        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
        }else if(player.outOfTime()){
            wantToQuit = true;
        }else if(nextRoom == null) {
            System.out.println("There is no door!");
        }else{
            player.setRoom(nextRoom);
            System.out.println(player.getRoom().getLongDescription());
            player.decrementTime();
            if(!rooms.contains(player.getRoom())){
                System.out.println("You are now beeing teleported");
                player.setRoom(randomRoom());
                System.out.println(player.getRoom().getLongDescription());
                player.decrementTime();
                player.clearStack();
            }
        }
    }
    
       private Room randomRoom(){
        int randomIndex = new Random().nextInt(rooms.size());
        Room randomRoom = rooms.get(randomIndex);
        return randomRoom;
    }
    
    private void charge(){
        if(player.getInventoryName().contains("Beamer")){
            roomDestination = player.getRoom();
            System.out.println("Beamer is charged");
        }else{
            System.out.println("Beamer is not in inventory");
        }
    }
    
    private void fire(){
        if(player.getInventoryName().contains("Beamer") && roomDestination != null){
            player.setRoom(roomDestination);
            System.out.println(player.getRoom().getLongDescription());
            player.decrementTime();
        }
    }
    
    private void eatCookie(Command command){
        if(!command.hasSecondWord()){
            System.out.println("What item?");
        }else if(command.hasSecondWord() && command.getSecondWord().equals("Magic-Cookie")){
            player.increasMaxWeight(player.getInventoryItem(command.getSecondWord()));
        }else{
            System.out.println("Not a valid item");
        }
    }

    private void take(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("What item?");
        }else if(player.inventoryWeight() + player.getRoom().getItemWeight(player.getRoom().getItem(command.getSecondWord())) > player.getMaxWeight()){
            System.out.println("You weak fuck, this is way to heavy");
        }else if(command.hasSecondWord() && player.getRoom().getItemName().contains(command.getSecondWord())){    
            player.pickUpItem(player.getRoom().getItem(command.getSecondWord()));
            player.inventoryWeight();
        }else{
            System.out.println("Not a valid item");
        }
    }

    private void drop(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("What item?");
            return;
        }else if(player.returnInventory().size() < 1){
            System.out.println("No items to drop.");
        }else if(command.hasSecondWord() && player.getInventoryName().contains(command.getSecondWord())){    
            player.dropItem(player.getInventoryItem(command.getSecondWord()));
            player.inventoryWeight();
        }else{
            System.out.println("Not a valid item");
        }
    }

    private void look(){
        System.out.println(player.getRoom().getLongDescription());
    }

    private void getItemInfo(Command command){
        if(command.hasSecondWord()){
            System.out.println("What item?");
        }

        if(player.getRoom().getItemsSize() <= 0){
            System.out.println("There are no items in this room.");
        }else{
            System.out.println(player.getRoom().getItemString());
        }
    }

    private boolean quit(Command command){
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }else {
            return true;  // signal that we want to quit
        }
    }
}
