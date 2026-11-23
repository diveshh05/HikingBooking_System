package app;

import data.DataStore;
import model.Difficulty;
import model.Tour;
import model.Trail;
import util.InputHelper;

public class HikingApp {

    private static DataStore store = new DataStore();

    public static void main(String[] args) {

        store.loadAll();

        boolean running = true;
        while(running){
            System.out.println("\n====== Main Menu =====");
            System.out.println("1. Manage Trails");
            System.out.println("2. Manage Guides");
            System.out.println("3. Manage Hikers");
            System.out.println("4. Manage Tours");
            System.out.println("5. Book Group Tour");
            System.out.println("6. Cancel a Booking");
            System.out.println("7. Cancel a Tour (Weather");
            System.out.println("8. Reports");
            System.out.println("0. Save and Exit");

            int choice = InputHelper.readInt("Choose (0-8): ", 0, 8);

            switch(choice){
                case 1:
                    trailMenu();
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    System.out.println("Coming soon...");
                    break;
                case 0:
                    store.saveAll();
                    System.out.println("Data saved. GoodBye!");
                    running = false;
                    break;
            }
        }
    }
    private static void trailMenu(){
        while(true){
            System.out.println("\n----- Manage Trails -----");
            System.out.println("1. Add");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("0. Back");
            int choice = InputHelper.readInt("Choose (0-4): ", 0,4);

            switch(choice){
                case 1:
                    addTrail();
                    break;
                case 2:
                    viewTrails();
                    break;
                case 3:
                    updateTrails();
                    break;
                case 4:
                    deleteTrail();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void addTrail(){
        String name = InputHelper.readString("Trail name: ");
        String location = InputHelper.readString("Location: ");
        Difficulty difficulty = InputHelper.readDifficulty("Difficulty: ");
        double price = InputHelper.readPositiveDouble("Price per person (Rs): ");
        double distance = InputHelper.readPositiveDouble("Distance (km): ");

        try{
            Trail t = new Trail(store.nextTrailId(), name, location, difficulty, price, distance);
            store.addTrail(t);
            store.saveAll();
            System.out.println("Saved: " + t);
        } catch (Exception e) {
            System.out.println("Not saved: " + e.getMessage());
        }
    }
    private static void viewTrails(){
        if(store.getAllTrails().isEmpty()){
            System.out.println("No trails yet.");
            return;
        }
        for(Trail t : store.getAllTrails()){
            System.out.println(t);
        }
    }
    private static void updateTrails(){
        String id = InputHelper.readString("Trail ID to update: ").toUpperCase();
        Trail t = store.getTrail(id);
        if(t == null){
            System.out.println("Not found.");
            return;
        }
        System.out.println("Current: " + t);
        System.out.println("1. Name 2. Location 3. Difficulty 4. Price 5. Distance ");
        int field = InputHelper.readInt("What to change (1-5): ", 1,5);

        try{
            if(field == 1){
                t.setName(InputHelper.readString("New name: "));
            }
            else if (field == 2) {
                t.setLocation(InputHelper.readString("New location: "));
            }
            else if (field == 3) {
                t.setDifficulty(InputHelper.readDifficulty("New difficulty: "));
            }
            else if (field == 4) {
                t.setPricePerPerson(InputHelper.readPositiveDouble("New price (Rs): "));
            }
            else {
                t.setDistanceKm(InputHelper.readPositiveDouble("New distance (km): "));
            }
            store.saveAll();
            System.out.println("Saved: " + t);
        } catch (Exception e) {
            System.out.println("Not changed: " + e.getMessage());
        }
    }
    private static void deleteTrail(){
        String id = InputHelper.readString("Trail ID to delete: ").toUpperCase();
        if(store.getTrail(id) == null){
            System.out.println("Not found");
            return;
        }
        for(Tour tour : store.getAllTours()){
            if(tour.getTrail().getId().equals(id)){
                System.out.println("Cannot delete: trails is used by tour " + tour.getId());
                return;
            }
        }
        store.deleteTrail(id);
        store.saveAll();
        System.out.println("Deleted " + id);
    }
}