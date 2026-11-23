package util;

import model.Difficulty;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputHelper {

    private static Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt, int min, int max){
        while(true){
            System.out.println(prompt);
            String line = scanner.nextLine().trim();
            try{
                int value = Integer.parseInt(line);
                if(value >=min && value<=max){
                    return value;
                }
                System.out.println("Please enter a number from " + min + " to " + max + ".");
            }
            catch(NumberFormatException e){
                System.out.println("Please try again. This is not a whole number");
            }
        }
    }

    public static double readPositiveDouble(String prompt){
        while(true){
            System.out.println(prompt);
            String line = scanner.nextLine().trim();
            try{
                double value = Double.parseDouble(line);
                if(value > 0){
                    return value;
                }
                System.out.println("Please enter a number more than 0");
            }
            catch(NumberFormatException e){
                System.out.println("Please try again. this is not a number");
            }
        }
    }

    public static String readString(String prompt){
        while(true){
            System.out.println(prompt);
            String line = scanner.nextLine().trim();
            if(!line.isEmpty()){
                return line;
            }
            System.out.println("This cannot be empty. try again");
        }
    }

    public static Difficulty readDifficulty(String prompt){
        System.out.println("1 = Easy, 2 = Moderate, 3 = Hard");
        int choice = readInt(prompt, 1, 3);
        return Difficulty.values()[choice -1];
    }

    public static String readDate(String prompt){
        while(true){
            System.out.println(prompt);
            String line = scanner.nextLine().trim();

            try{
                LocalDate date = LocalDate.parse(line);
                if(date.isBefore(LocalDate.now())){
                    System.out.println("The date cannot be in the past.");
                }
                else{
                    return date.toString();
                }
            }
            catch(DateTimeParseException e){
                System.out.println("Please use the format YYYY-MM-DD, e.g. 2026-11-23");
            }
        }
    }

}
