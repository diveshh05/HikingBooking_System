package model;

public class Trail {

    private String id;
    private String name;
    private String location;
    private Difficulty difficulty;
    private double pricePerPerson;
    private double distanceKm;

    public Trail(String id, String name, String location, Difficulty difficulty, double pricePerPerson, double distanceKm){
        this.id = id;
        setName(name);
        setLocation(location);
        setDifficulty(difficulty);
        setPricePerPerson(pricePerPerson);
        setDistanceKm(distanceKm);
    }

    //getters
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getLocation(){      // NEW
        return location;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }
    public double getPricePerPerson(){
        return pricePerPerson;
    }
    public double getDistanceKm() {
        return distanceKm;
    }

    public void setName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(name.contains(";")){
            throw new IllegalArgumentException("Name cannot contain ;");
        }
        this.name = name.trim();
    }

    public void setLocation(String location){
        if(location == null || location.trim().isEmpty()){
            throw new IllegalArgumentException("Location cannot be empty");
        }
        if(location.contains(";")){
            throw new IllegalArgumentException("Location cannot contain ;");
        }
        this.location = location.trim();
    }


    public void setDifficulty(Difficulty difficulty){
        if(difficulty == null){
            throw new IllegalArgumentException("Difficulty is required");
        }
        this.difficulty = difficulty;
    }

    public void setPricePerPerson(double pricePerPerson){
        if(pricePerPerson <= 0){
            throw new IllegalArgumentException("Price must be more than 0");
        }
        this.pricePerPerson = pricePerPerson;
    }

    public void setDistanceKm(double distanceKm) {
        if(distanceKm <= 0){
            throw new IllegalArgumentException("Distance must be more than 0");
        }
        this.distanceKm = distanceKm;
    }


    @Override
    public String toString(){
        return "ID: " + id + " | Name: " + name + " | Location: " + location + " | Difficulty: " + difficulty + " | Price: Rs " + String.format("%.2f", pricePerPerson) + " | Distance: " + distanceKm + " km";
    }
}
