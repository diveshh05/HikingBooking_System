package model;

import java.util.ArrayList;

public class Guide extends Person {

    private int experienceYears;
    private Difficulty maxDifficulty;
    private double dailyRate;

    private ArrayList<String> busyDates = new ArrayList<>();

    //constructors
    public Guide(String personID, String name, String phone, int experienceYears, Difficulty maxDifficulty, double dailyRate){
        super(personID, name, phone);

        setExperienceYears(experienceYears);
        setMaxDifficulty(maxDifficulty);
        setDailyRate(dailyRate);
    }
    // encapsulation
    public int getExperienceYears(){
        return experienceYears;
    }
    public Difficulty getMaxDifficulty(){
        return maxDifficulty;
    }
    public double getDailyRate(){
        return dailyRate;
    }

    public void setExperienceYears(int experienceYears){
        if(experienceYears < 0){
            throw new IllegalArgumentException("Experience years cannot be negative");
        }
            this.experienceYears = experienceYears;
    }

    public void setMaxDifficulty(Difficulty maxDifficulty){
        if( maxDifficulty == null){
            throw new IllegalArgumentException("Max Difficulty is required");
        }
            this.maxDifficulty = maxDifficulty;
    }

    public void setDailyRate(double dailyRate){
        if(dailyRate <= 0){
            throw new IllegalArgumentException("Daily rate must be more than o");
        }
            this.dailyRate = dailyRate;
    }

    @Override
    public String getRole() {
        return "Guide";
    }
    public boolean canLead(Difficulty level){
        return maxDifficulty.compareTo(level) >= 0;
    }

    public boolean isAvailable(String date){
        return !busyDates.contains(date);
    }

    public void bookDate(String date){
        if(!isAvailable(date)){
            throw new IllegalArgumentException(getName() + " is busy on " + date);
        }
        busyDates.add(date);
    }

    public void releaseDate(String date){
        busyDates.remove(date);
    }

    @Override
    public String toString(){
        return super.toString() + " | Role: " + getRole() + " | Experience: " + experienceYears + " | Max Difficulty: " + maxDifficulty
                + " | Daily Rate: Rs " + String.format("%.2f", dailyRate);
    }
}
