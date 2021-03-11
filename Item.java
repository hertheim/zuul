
public class Item{
    protected String name;
    protected String description;
    private int weight;
    
    public Item(String name, String description, int weight){
        this.name = name;
        this.description = description;
        this.weight = weight;
    }
    
    /**
     * @return the name of this artifact.
     */
    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }
    
    public int getWeight(){
        return weight;
    }
    
    public String getItemDescription(){
        return "There is a " + description + " and it weights " + weight + " kg.\n";
    }
}

