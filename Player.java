import java.util.HashMap;
import java.util.Stack;
import java.util.ArrayList;

public class Player{
    private String name;
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> rooms;
    private ArrayList<Item> items;
    private int maxWeight = 100;
    private int timeLimit = 20;

    public Player(String name,Room room){
        this.name = name;
        currentRoom = room;
        rooms = new Stack<Room>();
        items = new ArrayList<Item>();
    }

    public void goBack(){
        if (rooms.empty()){
            System.out.println("You have nowhere to go back to!");
        }else{
            previousRoom = rooms.pop();
            enterRoom(previousRoom);
        }
    }

    private void enterRoom(Room nextRoom){
        previousRoom = currentRoom;
        currentRoom = nextRoom;
        System.out.println(currentRoom.getLongDescription());      
    }
    
    public void clearStack(){
        rooms.clear();
    }

    public void pickUpItem(Item item){
        items.add(item);
        currentRoom.removeItem(item);
    }
    
    public int getMaxWeight(){
        return maxWeight;
    }
    
    public boolean outOfTime(){
        if(timeLimit == 0){
            return true;
        }else{
            return false;
        }
    }
    
    public void decrementTime(){
        timeLimit = timeLimit - 1;
    }

    public void dropItem(Item item){
        currentRoom.addItem(item);
        items.remove(item);
    }
    
    public void increasMaxWeight(Item item){
        maxWeight = maxWeight + 50;
        items.remove(item);
    }
    
    public int inventoryWeight(){
        int currentWeight = 0;
        for(Item item : items){
            currentWeight += item.getWeight();    
        }
        return currentWeight;
    }
    
    public Item getInventoryItem(String name){
        for(Item item : items){
            if(item.getName().equals(name)){
                return item;
            }
        }
        
        // if we get to this point no item with this name exists.
        return null;
    }
    
    public ArrayList<Item> returnInventory(){
        return items;
    }
    
    public String getInventoryName(){
        for(Item item : items){
            return item.getName();
        }
        return null;
    }
    
    
     
    public void showInventory(){
        for(Item item : items){
            System.out.println(item.getName());
        }
        System.out.println("Current weight of inventory is : " + inventoryWeight());
    }

    public void setRoom(Room nextRoom){
        rooms.push(currentRoom);
        currentRoom = nextRoom;
    }

    public Room getRoom(){
        return currentRoom;
    }

}
