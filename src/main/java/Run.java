//import java.io.File;
//import java.util.Scanner;
//import java.util.logging.Logger;
//
//public class Run {
//
//    private static final Logger log = Logger.getLogger(Encode.class.getName());
//    private static String COVER_IMAGE_PATH = "src/main/java/images/butterfly.bmp";
//    private static String INPUT_MESSAGE_PATH = "src/main/java/textfiles/example2.txt";
//    private static String ENCODED_MESSAGE_PATH = COVER_IMAGE_PATH.replace(".bmp", "_encode.bmp");
//
//    public static void main(String[] args) {
////        encodeFile(COVER_IMAGE_PATH, INPUT_MESSAGE_PATH);
//        mainMenu();
//    }
//
//    private static void mainMenu() {
//        Scanner input = new Scanner(System.in);
//        System.out.println("Would you like encode (0) or decode (1)?");
//        String choice = null;
//        while (choice == null || (!choice.equals("0") && !choice.equals("1"))) {
//            choice = input.nextLine();
//            switch (choice) {
//                case "0":
//                    System.out.println("Please select a 24-bit cover image stored in src/main/java/images/");
//                    System.out.println("e.g. for src/main/java/images/cover_image.bmp please type 'cover_image.bmp'");
//                    COVER_IMAGE_PATH = fileSelection("src/main/java/images/");
//                    log.info("coverImagePath: " + COVER_IMAGE_PATH);
//                    System.out.println("Please select the file you wish to hide in the cover image, stored in src/main/java/images/");
//                    System.out.println("e.g. for src/main/java/files/test.text please type 'test.txt'");
//                    String INPUT_MESSAGE_PATH = fileSelection("src/main/java/files/");
//                    log.info("inputFilePath: " + INPUT_MESSAGE_PATH);
//                    encodeFile(COVER_IMAGE_PATH, INPUT_MESSAGE_PATH);
//                    break;
//                case "1":
//                    System.out.println("Please select a 24-bit cover image stored in src/main/java/images/ to decode");
//                    System.out.println("e.g. for src/main/java/images/file_to_decode.bmp please type 'file_to_decode.bmp'");
//                    COVER_IMAGE_PATH = fileSelection("src/main/java/images/");
//                    break;
//                default:
//                    System.out.println("Incorrect input, please type 0 to encode a message and 1 to decode a message.");
//                    // The user input an unexpected choice.
//            }
//        }
//    }
//
//    private static String fileSelection(String pathFolder) {
//        Scanner input = new Scanner(System.in);
//        String path = null;
//        File f = null;
//        while (f == null || !f.exists() || path.trim().equals(pathFolder)) {
//            path = pathFolder + input.nextLine();
//            f = new File(path);
//            if (!f.exists()) {
//                System.out.println("file: " + path + " doesn't exist! Please try again.");
//            }
//        }
//        return path;
//    }
//
//
//}
