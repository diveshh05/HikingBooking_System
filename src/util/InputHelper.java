package util;

import model.Difficulty;

import java.util.Scanner;

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

}
