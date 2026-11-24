package app;

import data.DataStore;
import model.*;
import util.InputHelper;
import service.BookingService;

public class HikingApp {

    private static DataStore store = new DataStore();
    private static BookingService bookingService = new BookingService(store);

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
                    guideMenu();
                    break;
                case 3:
                    hikerMenu();
                    break;
                case 4:
                    tourMenu();
                    break;
                case 5:
                    bookingService.bookGroup();
                    break;
                case 6:
                    bookingService.cancelBooking();
                    break;
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


    private static void guideMenu(){

        while(true){
            System.out.println("\n----- Manage Guides -----");
            System.out.println("1. Add");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("0. Back");
            int choice = InputHelper.readInt("Choose (0-4): ", 0,4);

            switch(choice){
                case 1:
                    addGuide();
                    break;
                case 2:
                    viewGuides();
                    break;
                case 3:
                    updateGuide();
                    break;
                case 4:
                    deleteGuide();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void addGuide(){
        String name = InputHelper.readString("Guide name: ");
        String phone = InputHelper.readString("Phone (8 digits): ");
        int experience = InputHelper.readInt("Years of experience (0-50): ", 0, 50);
        Difficulty maxDifficulty = InputHelper.readDifficulty("Hardest trail they can lead: ");
        double rate = InputHelper.readPositiveDouble("Daily rate (Rs): ");

        try{
            Guide g = new Guide(store.nextGuideId(), name, phone, experience, maxDifficulty, rate);
            store.addGuide(g);
            store.saveAll();
            System.out.println("Saved: " + g);
        } catch (Exception e) {
            System.out.println("Not saved: " + e.getMessage());
        }
    }
    public static void viewGuides(){
        if(store.getAllGuides().isEmpty()){
            System.out.println("No guides yet.");
            return;
        }
        for(Guide g : store.getAllGuides()){
            System.out.println(g);
        }
    }
    public static void updateGuide(){
        String id = InputHelper.readString("Guide ID to update: ").toUpperCase();
        Guide g = store.getGuide(id);
        if(g == null){
            System.out.println("Not found.");
            return;
        }
        System.out.println("Current: " + g);
        System.out.println("1. Name 2. Phone 3. Experience 4. Max Difficulty 5. 5. Daily rate");
        int field = InputHelper.readInt("What to change (1-5): ", 1,5);

        try{
            if(field == 1){
                g.setName(InputHelper.readString("New name: "));
            }
            else if(field == 2){
                g.setPhone(InputHelper.readString("New phone (8 digits): "));
            }
            else if(field == 3){
                g.setExperienceYears(InputHelper.readInt("New experience (0-50): ", 0, 50));
            }
            else if(field == 4){
                g.setMaxDifficulty(InputHelper.readDifficulty("New max difficulty: "));
            }
            else{
                g.setDailyRate(InputHelper.readPositiveDouble("New daily rate (Rs): "));
            }
            store.saveAll();
            System.out.println("Saved: " + g);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Not changed: " + e.getMessage());
        }
    }
    private static void deleteGuide(){
        String id = InputHelper.readString("Guide ID to delete: ").toUpperCase();
        if(store.getGuide(id) == null){
            System.out.println("Not found.");
            return;
        }
        for(Tour tour : store.getAllTours()){
            if(tour.getGuide().getId().equals(id)){
                System.out.println("Cannot delete: guide leads tour " + tour.getId());
                return;
            }
        }
        store.deleteGuide(id);
        store.saveAll();
        System.out.println("Deleted " + id);
    }


    public static void hikerMenu(){
        while (true){
            System.out.println("\n----- Manage Hikers -----");
            System.out.println("1. Add");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("0. Back");
            int choice = InputHelper.readInt("Choose (0-4): ", 0,4);

            switch(choice){
                case 1:
                    addHiker();
                    break;
                case 2:
                    viewHikers();
                    break;
                case 3:
                    updateHiker();
                    break;
                case 4:
                    deleteHiker();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void addHiker(){
        String name = InputHelper.readString("Hiker name: ");
        String phone = InputHelper.readString("Phone (8 digits): ");
        int age = InputHelper.readInt("Age (12-75): ", 12, 75);
        Difficulty fitness = InputHelper.readDifficulty("Fitness level: ");

        try{
            Hiker h = new Hiker(store.nextHikerId(), name, phone, age, fitness);
            store.addHiker(h);
            store.saveAll();
            System.out.println("Saved: " + h);
        } catch (IllegalArgumentException e) {
            System.out.println("Not saved: " + e.getMessage());
        }
    }
    public static void viewHikers(){
        if(store.getAllHikers().isEmpty()){
            System.out.println("No hikers yet.");
            return;
        }
        for(Hiker h : store.getAllHikers()){
            System.out.println(h);
        }
    }
    private static void updateHiker(){
        String id = InputHelper.readString("Hiker ID to update: ").toUpperCase();
        Hiker h = store.getHiker(id);
        if(h == null){
            System.out.println("Not found.");
            return;
        }
        System.out.println("Current: " + h);
        System.out.println("1. Name  2. Phone  3. Age  4. Fitness level");
        int field = InputHelper.readInt("What to change (1-4): ", 1, 4);

        try{
            if(field == 1){
                h.setName(InputHelper.readString("New name: "));
            }
            else if(field == 2){
                h.setPhone(InputHelper.readString("New phone (8 digits): "));
            }
            else if(field == 3){
                h.setAge(InputHelper.readInt("New age (12-75): ", 12, 75));
            }
            else{
                h.setFitnessLevel(InputHelper.readDifficulty("New fitness level: "));
            }
            store.saveAll();
            System.out.println("Saved: " + h);
        } catch (IllegalArgumentException e) {
            System.out.println("Not changed: " + e.getMessage());
        }
    }
    public static void deleteHiker(){
        String id = InputHelper.readString("Hiker ID to delete: ").toUpperCase(); // errorr
        if(store.getHiker(id) == null){
            System.out.println("Not found.");
            return;
        }
        for(Booking b : store.getAllBookings()){
            for(Hiker h : b.getHikers()){
                if(h.getId().equals(id)){
                    System.out.println("Cannot delete: hiker is in booking " + b.getId());
                    return;
                }
            }
        }
        for(Tour tour : store.getAllTours()){
            for(Hiker h : tour.getWaitingHikers()){
                if(h.getId().equals(id)){
                    System.out.println("Cannot delete: hiker is on the waiting list of " + tour.getId());
                    return;
                }
            }
        }
        store.deleteHiker(id);
        store.saveAll();
        System.out.println("Deleted " + id);
    }


    public static void tourMenu(){
        while (true){
            System.out.println("\n----- Manage Tours -----");
            System.out.println("1. Add");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("0. Back");
            int choice = InputHelper.readInt("Choose (0-4): ", 0,4);

            switch(choice){
                case 1:
                    addTour();
                    break;
                case 2:
                    viewTours();
                    break;
                case 3:
                    updateTour();
                    break;
                case 4:
                    deleteTour();
                    break;
                case 0:
                    return;
            }
        }
    }
    private static void addTour(){
        //1
        Trail trail = store.getTrail(InputHelper.readString("Trail ID: ").toUpperCase());
        if(trail == null){
            System.out.println("Trail not found.");
            return;
        }
        //2
        Guide guide = store.getGuide(InputHelper.readString("Guide ID: ").toUpperCase());
        if(guide == null){
            System.out.println("Guide not found.");
            return;
        }
        //3
        if(!guide.canLead(trail.getDifficulty())){
            System.out.println(guide.getName() + " cannot lead a " + trail.getDifficulty() + " trail.");
            return;
        }
        //4
        String date = InputHelper.readDate("Date (YYYY-MM-DD): ");
        //5
        if(!guide.isAvailable(date)){
            System.out.println(guide.getName() + " is already busy on " + date + ".");
            return;
        }
        int maxPlaces = InputHelper.readInt("Maximum places (1-30): ", 1,30);

        try{
            Tour tour = new Tour(store.nextTourId(), trail, guide, date, maxPlaces);
            guide.bookDate(date);
            store.addTour(tour);
            store.saveAll();
            System.out.println("Saved: " + tour);
        } catch (IllegalArgumentException e) {
            System.out.println("Not saved: " + e.getMessage());
        }
    }
    private static void viewTours(){
        if(store.getAllTours().isEmpty()){
            System.out.println("No tours yet.");
            return;
        }
        for(Tour t : store.getAllTours()){
            System.out.println(t);
        }
    }
    private static void updateTour(){
        String id = InputHelper.readString("Tour ID to update: ").toUpperCase();
        Tour tour = store.getTour(id);
        if(tour == null){
            System.out.println("Not found.");
            return;
        }
        if(tour.getStatus() == TourStatus.CANCELLED){
            System.out.println("This tour is cancelled and cannot be changed.");
            return;
        }
        System.out.println("Current: " + tour);
        System.out.println("1. Date 2. Guide 3. Maximum Places");
        int field = InputHelper.readInt("What to change (1-3)", 1,3);

        try{
            Guide guide = tour.getGuide();
            if(field == 1){
                String newDate = InputHelper.readDate("New date (YYYY-MM-DD): ");
                if(!guide.isAvailable(newDate)){
                    System.out.println(guide.getName() + " is already busy on " + newDate + ".");
                    return;
                }
                guide.releaseDate(tour.getDate());
                guide.bookDate(newDate);
                tour.setDate(newDate);
            }
            else if(field == 2){
                Guide newGuide = store.getGuide(InputHelper.readString("New guide ID: ").toUpperCase());
                if(newGuide == null){
                    System.out.println("Guide not found.");
                    return;
                }
                if(!newGuide.canLead(tour.getTrail().getDifficulty())){
                    System.out.println(newGuide.getName() + " cannot lead a " + tour.getTrail().getDifficulty() + " trail.");
                    return;
                }
                if(!newGuide.isAvailable(tour.getDate())){
                    System.out.println(newGuide.getName() + " is already busy on " + tour.getDate() + ".");
                    return;
                }
                guide.releaseDate(tour.getDate());   // old guide is free again
                newGuide.bookDate(tour.getDate());   // new guide is now busy
                tour.setGuide(newGuide);
            }
            else{
                int booked = tour.getMaxPlaces() - tour.getFreePlaces();
                int newMax = InputHelper.readInt("New maximum places(1-30): ", 1,30);
                if(newMax < booked){
                    System.out.println("Cannot go below " + booked + ": that many places are already booked");
                    return;
                }
                tour.setMaxPlaces(newMax);
                tour.setFreePlaces(newMax - booked);
            }
            store.saveAll();
            System.out.println("Saved: " + tour);
        } catch (IllegalArgumentException e) {
            System.out.println("Not changed: " + e.getMessage());
        }
    }
    private static void deleteTour(){
        String id = InputHelper.readString("Tour ID to delete: ").toUpperCase();
        Tour tour = store.getTour(id);
        if(tour == null){
            System.out.println("Not found.");
            return;
        }
        if(!store.getBookingsForTour(id).isEmpty()){
            System.out.println("Cannot delete: this tour has bookings. Cancel it instead (menu 7)");
            return;
        }
        tour.getGuide().releaseDate(tour.getDate());
        store.deleteTour(id);
        store.saveAll();
        System.out.println("Deleted " + id);
    }
}