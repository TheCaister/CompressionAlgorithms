import java.util.Random;
import java.io.File;
import java.io.IOException;

public class InputGenerator {

    /**
     * Generates a text file with random characters
     *
     * @param size The amount of characters you want the final text
     *             file to include.
     */
    public static void GenerateRandomText(int size, String fileName){
        Random random = new Random();

        // Getting a random character
        for(int i = 0; i < size; i++){
            char randomCharacter = (char) (random.nextInt(26) + 'a');
            System.out.print(randomCharacter);
        }

        System.out.println();
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
