import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;

public class Driver {
    private static final Logger log;
    //predefined for testing purposes
    private static String COVER_IMAGE_PATH = "src/main/java/images/butterfly.bmp";
    private static String INPUT_MESSAGE_PATH = "src/main/java/files/example2.txt";
    private static String INPUT_IMAGE_PATH = "src/main/java/images/butterfly_encode.bmp";
    private static String IMAGE_FOLDER = "src/main/java/images/";
    private static String FILES_FOLDER = "src/main/java/files/";

    static {
        String path = Driver.class.getClassLoader()
                .getResource("logging.properties")
                .getFile();
        System.setProperty("java.util.logging.config.file", path);
        log = Logger.getLogger(Driver.class.getName());
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Would you like encode (0) or decode (1)?");
        String choice = input.nextLine();
        switch (choice) {
            case "0":
                System.out.println("Please select a 24-bit cover image stored in " + IMAGE_FOLDER);
                System.out.println("e.g. for src/main/java/images/test.bmp please type 'test.bmp'");
                COVER_IMAGE_PATH = fileSelection(IMAGE_FOLDER);
                log.info("coverImagePath: " + COVER_IMAGE_PATH);
                System.out.println("Please select the file you wish to hide in the cover image, stored in " + FILES_FOLDER);
                System.out.println("e.g. for src/main/java/files/test.text please type 'test.txt'");
                INPUT_MESSAGE_PATH = fileSelection(FILES_FOLDER);
                log.info("inputFilePath: " + INPUT_MESSAGE_PATH);
                Encode.encodeFile(COVER_IMAGE_PATH, INPUT_MESSAGE_PATH);
                break;
            case "1":
                System.out.println("Please select a 24-bit image that you wish to decode. (Stored in " + IMAGE_FOLDER);
                System.out.println("e.g. for src/main/java/images/image_to_decrypt.bmp please type 'test.bmp'");
                INPUT_IMAGE_PATH = fileSelection(IMAGE_FOLDER);
                Decode.decodeFile(INPUT_IMAGE_PATH);
                break;
            default:
                System.out.println("Incorrect input, please type 0 to encode a message and 1 to decode a message.");
        }
    }

    private static String fileSelection(String pathFolder) {
        Scanner input = new Scanner(System.in);
        String path = null;
        File f = null;
        while (f == null || !f.exists() || path.trim().equals(pathFolder)) {
            path = pathFolder + input.nextLine();
            f = new File(path);
            if (pathFolder.equals(IMAGE_FOLDER) && !path.endsWith(".bmp")) {
                f = null;
                System.out.println("file: " + path + " isn't a .bmp file! Please try again.");
            } else if (!f.exists()) {
                System.out.println("file: " + path + " doesn't exist! Please try again.");
            }
        }
        return path;
    }

}
