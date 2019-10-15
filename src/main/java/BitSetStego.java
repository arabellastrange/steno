import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;
import java.util.logging.Logger;


public class BitSetStego {
    private static final Logger log = Logger.getLogger(BitSetStego.class.getName());

    public static void main(String[] args) {
        mainMenu();
//        encodeFile("src/main/java/images/all_white_test.bmp", "src/main/java/textfiles/example1.txt");
    }

    private static void mainMenu(){
        Scanner input = new Scanner(System.in);
        System.out.println("Would you like encode (0) or decode (1)?");
        String choice = input.nextLine();
        switch (choice) {
            case "0":
                System.out.println("Please select a 24-bit cover image stored in src/main/java/images/");
                System.out.println("e.g. for src/main/java/images/test.bmp please type 'test.bmp'");
                String coverImagePath = fileSelection("src/main/java/images/");
                log.info("coverImagePath: " + coverImagePath);
                System.out.println("Please select the file you wish to hide in the cover image, stored in src/main/java/images/");
                System.out.println("e.g. for src/main/java/files/test.text please type 'test.txt'");
                String inputFilePath = fileSelection("src/main/java/files/");
                log.info("inputFilePath: " + inputFilePath);
//                encodeFile(coverImagePath, inputFilePath);
                break;
            case "1":

//                if (choice = )
                break;
            default:
                System.out.println("Incorrect input, please type 0 to encode a message and 1 to decode a message.");
                // The user input an unexpected choice.
        }
    }

    private static String fileSelection(String pathFolder){
        Scanner input = new Scanner(System.in);
        String path = null;
        File f = null;
        while (f == null || !f.exists() || path.trim().equals(pathFolder)) {
            path = pathFolder + input.nextLine();
            f = new File(path);
            if  (!f.exists()) {
                System.out.println("file: " + path + " doesn't exist! Please try again.");
            }
        }
        return path;
    }

    private static void decodeFile(String coverImagePath) throws IOException {
        byte[] coverImageBytes = processInput(coverImagePath);
        OutputStream out = OutputStream.nullOutputStream();
        try {
            out = new FileOutputStream("output path");
            out.write(decodeFile(coverImageBytes));
        } finally {
            out.close();
        }
    }

    private static byte[] decodeFile(byte[] coverImageBytes) {
        //TODO
        return new byte[]{};
    }

    private static void encodeFile(String coverImagePath, String inputPath) {
        try {
            byte[] coverImageBytes = processInput(coverImagePath);
            byte[] fileBytes = processInput(inputPath);
            encodeFile(coverImageBytes, fileBytes);
        } catch (IOException e) {
            log.warning("IO Exception: " + e.toString());
        }
    }

    private static void encodeFile(byte[] coverImage, byte[] input) {
        // start at 54 to skip header of image, split into header and image
        byte[] header = Arrays.copyOfRange(coverImage, 0, 54);
        byte[] image = Arrays.copyOfRange(coverImage, 54, coverImage.length);
        log.info("Original image header " + header[0]);
        log.info("Example byte of image[0]: " + image[0]);
        log.info("Example byte of input [0]: " + input[0]);

        if ((image.length)/8 > input.length) {
            //convert image bytes[] to bits
            BitSet imageBits = byteToBitConverter(image);
            log.info("Example bit of image[0] " + imageBits.get(0));

            BitSet inputBits = byteToBitConverter(input);
            log.info("Example bit of input " + inputBits.get(0));

            int inputBitPosition = 0;
            for (int i = 7; i < imageBits.size(); i = i + 7) {
                imageBits.set(i, inputBits.get(inputBitPosition));
                inputBitPosition++;
                if(inputBitPosition == inputBits.length()){
                    break;
                }
            }

            log.info("Example encoded bit of image [0] " + imageBits.get(0));

            //convert bits to Bytes[]
            byte[] newImageBytes = new byte[header.length + imageBits.length()];
            System.arraycopy(header, 0, newImageBytes, 0, header.length);
            System.arraycopy(imageBits.toByteArray(), 0, newImageBytes, header.length, imageBits.toByteArray().length);
            log.info("Modified image header " + newImageBytes[0]);
            renderOutputImage(newImageBytes);

        } else {
            log.warning("Image file too small to encode given input");
        }
    }

    private static BitSet byteToBitConverter(byte[] array) {
        return BitSet.valueOf(array);
    }

    private static byte[] processInput(String path) throws IOException {
        Path inputPath = Paths.get(path);
        InputStream inputStream = InputStream.nullInputStream();
        try {
            inputStream = Files.newInputStream(inputPath, StandardOpenOption.CREATE_NEW);
            log.info("processing input stream");
            return inputStream.readAllBytes();
        } finally {
            inputStream.close();
        }
    }

    private static void renderOutputImage(byte[] newImageBytes) {
        //TODO
//        log.info("newImageBytes: " + Arrays.toString(newImageBytes));
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(newImageBytes);
            BufferedImage bufferedImage = ImageIO.read(bis);
            ImageIO.write(bufferedImage, "bmp", new File("src/main/java/images/all_white_test_new.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        //bytes 18-21 represent width. 22-25 represent height
//        byte[] widthArray = Arrays.copyOfRange(newImageBytes, 18, 22);
//        byte[] heightArray = Arrays.copyOfRange(newImageBytes, 22, 26);
//
//        log.info("start of array: " +  Arrays.toString(Arrays.copyOfRange(newImageBytes, 0, 27)));
//        log.info("widthArray: " + Arrays.toString(widthArray));
//        log.info("heightArray: " + Arrays.toString(heightArray));
//        for ( int x = 0; x < widthArray.length; x++) {
//            log.info(widthArray[x]);
//        }
//        log.info("heightArray: " + heightArray.toString());
//        int width;
//        int height;
//        BufferedImage outputImage = new BufferedImage(newImageBytes.get(18), newImageBytes[22], );
//
//        try {
//            ImageIO.write(outputImage, "BMP", new File("src/main/java/images/output.bmp"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try
//        {
//            File ms = new File();
//            ms.Write(byteArrayIn, 0, byteArrayIn.Length);
//            returnImage = Image.FromStream(ms,true);//Exception occurs here
//        }
//        catch { }
    }
}
