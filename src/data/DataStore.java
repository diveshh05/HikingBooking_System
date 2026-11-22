package data;

import model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import payment.CardPayment;
import payment.CashPayment;
import payment.OnlinePayment;
import payment.Payment;

public class DataStore {

    private HashMap<String, Trail> trails = new HashMap<>();
    private HashMap<String, Guide> guides = new HashMap<>();
    private HashMap<String, Hiker> hikers = new HashMap<>();
    private HashMap<String, Tour> tours = new HashMap<>();
    private HashMap<String, Booking> bookings = new HashMap<>();



    private static final String FOLDER = "data/";


    public String generateId(String prefix, HashMap<String, ?> map){
        int number = 1;
        while(map.containsKey(String.format("%s%03d", prefix, number))){
            number++;
        }
        return String.format("%s%03d", prefix, number);
    }

    public String nextTrailId(){
        return generateId("T", trails);
    }
    public String nextGuideId(){
        return generateId("G", guides);
    }
    public String nextHikerId(){
        return generateId("H", hikers);
    }
    public String nextTourId(){
        return generateId("TR", tours);
    }
    public String nextBookingId(){
        return generateId("B", bookings);
    }

    // trails
    public void addTrail(Trail t){
        trails.put(t.getId(), t);
    }
    public Trail getTrail(String id){
        return trails.get(id);
    }
    public ArrayList<Trail> getAllTrails(){
        return new ArrayList<>(trails.values());
    }
    public boolean deleteTrail(String id){
        return trails.remove(id) != null;
    }

    // guides
    public void addGuide(Guide g){
        guides.put(g.getId(), g);
    }
    public Guide getGuide(String id){
        return guides.get(id);
    }
    public ArrayList<Guide> getAllGuides(){
        return new ArrayList<>(guides.values());
    }
    public boolean deleteGuide(String id){
        return guides.remove(id) != null;
    }


    // Hiker
    public void addHiker(Hiker h){
        hikers.put(h.getId(), h);
    }
    public Hiker getHiker(String id){
        return hikers.get(id);
    }
    public ArrayList<Hiker> getAllHikers(){
        return new ArrayList<>(hikers.values());
    }
    public boolean deleteHiker(String id){
        return hikers.remove(id) != null;
    }


    // Tours
    public void addTour(Tour t){
        tours.put(t.getId(), t);
    }
    public Tour getTour(String id){
        return tours.get(id);
    }
    public ArrayList<Tour> getAllTours(){
        return new ArrayList<>(tours.values());
    }
    public boolean deleteTour(String id){
        return tours.remove(id) != null;
    }


    // booking
    public void addBooking(Booking b){
        bookings.put(b.getId(), b);
    }
    public Booking getBooking(String id){
        return bookings.get(id);
    }
    public ArrayList<Booking> getAllBookings(){
        return new ArrayList<>(bookings.values());
    }
    public boolean deleteBooking(String id){
        return bookings.remove(id) != null;
    }

    public ArrayList<Booking> getBookingsForTour(String tourId){
        ArrayList<Booking> result = new ArrayList<>();
        for(Booking b : bookings.values()){
            if(b.getTour().getId().equals(tourId)){
                result.add(b);
            }
        }
        return result;
    }

    //trails.txt

    public void saveTrails(){
        new File(FOLDER).mkdirs();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER + "trails.txt"))){
            for(Trail t : trails.values()){
                writer.write(t.getId() + ";" + t.getName() + ";" + t.getLocation() + ";" + t.getDifficulty() + ";"
                        + t.getPricePerPerson() + ";" +t.getDistanceKm());
                writer.newLine();
            }
        }
        catch (IOException e){
            System.out.println("Could not save trails: " + e.getMessage());
        }
    }

    public void saveGuides(){
        new File(FOLDER).mkdirs();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER + "guides.txt"))){
            for(Guide g : guides.values()){
                writer.write(g.getId() + ";" + g.getName() + ";" + g.getPhone() + ";" + g.getExperienceYears() + ";" + g.getMaxDifficulty() + ";" + g.getDailyRate());
                writer.newLine();
            }
        }
        catch (IOException e){
            System.out.println("Could not save guides: " + e.getMessage());
        }
    }

    public void saveHikers(){
        new File(FOLDER).mkdirs();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER + "hikers.txt"))){
            for(Hiker h : hikers.values()){
                writer.write(h.getId() + ";" + h.getName() + ";" + h.getPhone() + ";" + h.getAge() + ";" + h.getFitnessLevel());
                writer.newLine();
            }
        }
        catch (IOException e){
            System.out.println("Could not save hikers: " + e.getMessage());
        }
    }

    //load

    public void loadTrails(){
        File file = new File(FOLDER + "trails.txt");
        if(!file.exists()){
            return;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){

            String line;
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }
                try{
                    String[] p = line.split(";");
                    Trail t = new Trail(p[0], p[1], p[2], Difficulty.valueOf(p[3]), Double.parseDouble(p[4]), Double.parseDouble(p[5]));
                    trails.put(t.getId(), t);
                }
                catch (Exception e){
                    System.out.println("Skipped bad line in trails.txt " + line);
                }
            }

        } catch (Exception e) {
            System.out.println("Could not load trails: " + e.getMessage());
        }
    }

    public void loadGuides(){
        File file = new File(FOLDER + "guides.txt");
        if(!file.exists()){
            return;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){

            String line;
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }
                try{
                    String[] p = line.split(";");
                    Guide g = new Guide(p[0], p[1], p[2], Integer.parseInt(p[3]), Difficulty.valueOf(p[4]), Double.parseDouble(p[5]));
                    guides.put(g.getId(), g);
                }
                catch (Exception e){
                    System.out.println("Skipped bad line in guides.txt " + line);
                }
            }

        } catch (Exception e) {
            System.out.println("Could not load guides: " + e.getMessage());
        }

    }


    public void loadHikers(){
        File file = new File(FOLDER + "hikers.txt");
        if(!file.exists()){
            return;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){

            String line;
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }
                try{
                    String[] p = line.split(";");
                    Hiker h = new Hiker(p[0], p[1], p[2], Integer.parseInt(p[3]), Difficulty.valueOf(p[4]));
                    hikers.put(h.getId(), h);
                }
                catch (Exception e){
                    System.out.println("Skipped bad line in hikers.txt " + line);
                }
            }

        } catch (Exception e) {
            System.out.println("Could not load hikers: " + e.getMessage());
        }
    }

    public void saveTours(){
        new File(FOLDER).mkdirs();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER + "tours.txt"))){

            for(Tour t : tours.values()){
                String waiting = "";
                for(Hiker h : t.getWaitingHikers()){
                    if(!waiting.isEmpty()){
                        waiting = waiting + ",";
                    }
                    waiting = waiting + h.getId();
                }
                if(waiting.isEmpty()){
                    waiting = "-";
                }
                writer.write(t.getId() + ";" + t.getTrail().getId() + ";" + t.getGuide().getId() + ";" + t.getDate() + ";" + t.getMaxPlaces() + ";"
                        + t.getFreePlaces() + ";" + t.getStatus() + ";" + waiting);
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Could not save tours: " + e.getMessage());
        }
    }

    public void saveBookings(){
        new File(FOLDER).mkdirs();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER + "bookings.txt"))){

            for(Booking b : bookings.values()){
                String waiting = "";

                String ids = "";
                for(Hiker h : b.getHikers()){
                    if(!ids.isEmpty()){
                        ids = ids + ",";
                    }
                    ids = ids + h.getId();
                }
                String method = "none";
                String detail = "-";
                if(b.getPayment() !=null){
                    method = b.getPayment().getMethod();
                    detail = b.getPayment().getDetail();
                }
                writer.write(b.getId() + ";" + b.getTour().getId() + ";" + ids + ";" + b.getTotalPrice() + ";"
                        + b.getStatus() + ";" + method + ";" + detail);
                writer.newLine();

            }
        } catch (Exception e) {
            System.out.println("Could not save bookings: " + e.getMessage());
        }
    }


    public void loadTours(){
        File file = new File(FOLDER + "tours.txt");
        if(!file.exists()){
            return;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }
                try{
                    String[] p = line.split(";");
                    Trail trail = trails.get(p[1]);
                    Guide guide = guides.get(p[2]);
                    if(trail == null || guide == null){
                        throw new IllegalArgumentException("unknown trail or guide");
                    }
                    Tour t = new Tour(p[0], trail, guide, p[3], Integer.parseInt(p[4]));
                    t.setFreePlaces(Integer.parseInt(p[5]));
                    if(TourStatus.valueOf(p[6]) == TourStatus.CANCELLED){
                        t.cancel();                          // keep it cancelled
                    }
                    else{
                        if(guide.isAvailable(p[3])){
                            guide.bookDate(p[3]);
                        }
                        if(!p[7].equals("-")){
                            for(String hikerId : p[7].split(",")){
                                Hiker h = hikers.get(hikerId);
                                if(h != null){
                                    t.addToWaitingList(h);
                                }
                            }
                        }
                    }
                    tours.put(t.getId(), t);
                }
                catch(Exception e){
                    System.out.println("Skipped bad line in tours.txt: " + line);
                }
            }
        }
        catch(IOException e){
            System.out.println("Could not load tours: " + e.getMessage());
        }
    }


    public void loadBookings(){
        File file = new File(FOLDER + "bookings.txt");
        if(!file.exists()){
            return;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }
                try{
                    String[] p = line.split(";");
                    Tour tour = tours.get(p[1]);
                    if(tour == null){
                        throw new IllegalArgumentException("unknown tour");
                    }
                    ArrayList<Hiker> group = new ArrayList<>();
                    for(String hikerId : p[2].split(",")){
                        Hiker h = hikers.get(hikerId);
                        if(h == null){
                            throw new IllegalArgumentException("unknown hiker");
                        }
                        group.add(h);
                    }
                    double total = Double.parseDouble(p[3]);
                    BookingStatus status = BookingStatus.valueOf(p[4]);
                    Payment payment = null;
                    if(p[5].equals("Cash")){
                        payment = new CashPayment(total, Double.parseDouble(p[6]));
                    }
                    else if(p[5].equals("Card")){
                        payment = new CardPayment(total, p[6]);
                    }
                    else if(p[5].equals("Online")){
                        payment = new OnlinePayment(total, p[6]);
                    }
                    Booking b = new Booking(p[0], tour, group, total, payment, status);
                    bookings.put(b.getId(), b);
                }
                catch(Exception e){
                    System.out.println("Skipped bad line in bookings.txt: " + line);
                }
            }
        }
        catch(IOException e){
            System.out.println("Could not load bookings: " + e.getMessage());
        }
    }

    public void saveAll(){
        saveTrails();
        saveGuides();
        saveHikers();
        saveTours();
        saveBookings();
    }
    public void loadAll(){
        loadTrails();
        loadGuides();
        loadHikers();
        loadTours();
        loadBookings();
    }

}
