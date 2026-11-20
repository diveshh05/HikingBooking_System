package model;

public class Hiker extends Person {

    // field
    private int age;
    private Difficulty fitnessLevel;

    //constructors
    public Hiker(String personID, String name, String phone, int age, Difficulty fitnessLevel){
        super(personID, name, phone); // the field that is private in person so Hiker can’t touch them directly
        setAge(age);
        setFitnessLevel(fitnessLevel);
    }

    //encapsulation
    public int getAge(){
        return age;
    }
    public Difficulty getFitnessLevel(){
        return fitnessLevel;
    }

    public void setAge(int age){
        if(age < 12 || age > 75){
            throw new IllegalArgumentException("Age must be between 12 to 75.");
        }
            this.age = age;
    }
    public void setFitnessLevel(Difficulty fitnessLevel){
        if(fitnessLevel == null){
            throw new IllegalArgumentException("Fitness level is required");
        }
            this.fitnessLevel = fitnessLevel;
    }

    @Override
    public String getRole() {
        return "Hiker";
    }
    public boolean canHandle(Difficulty trailDifficulty){
        return fitnessLevel.compareTo(trailDifficulty) >=0 ;
    }

    @Override
    public String toString(){
        return super.toString() + " | Role: " + getRole() + " | Age: " + age + " | Fitness Level: " + fitnessLevel;
    }
}