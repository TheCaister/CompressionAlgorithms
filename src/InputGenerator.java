import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputGenerator {

    /**
     * Generates a text file with random characters
     *
     * @param size The amount of characters you want the final text
     *             file to include.
     */
    public static void GenerateRandomText(int size, String fileName){
        fileName = "inputs/" + fileName + ".txt";
        try{
            File file = new File(fileName);
            if(file.createNewFile()){
                System.out.println("File successfully created: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred when trying to create file.");
            e.printStackTrace();
        }

        try{
            Random random = new Random();
            FileWriter writer = new FileWriter(fileName);
            // Getting a random character
            for(int i = 0; i < size; i++){
                char randomCharacter = (char) (random.nextInt(26) + 'a');
                writer.write(randomCharacter);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred when trying to write to file.");
            e.printStackTrace();
        }

        try{
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                System.out.println(fileName + ": " + reader.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when trying to read file.");
            e.printStackTrace();
        }


    }

    public static String GenerateRandomText(int size){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        // Getting a random character
        for(int i = 0; i < size; i++){
            char randomCharacter = (char) (random.nextInt(26) + 'a');
            builder.append(randomCharacter);
        }

        return builder.toString();
    }
}
