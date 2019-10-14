import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.logging.Logger;

public class Run {
    private final static Logger log = Logger.getLogger(Run.class.getName());

    public static void main(String[] args) {
       encodeFile("src/main/java/images/butterfly.bmp", "src/main/java/textfiles/example1.txt");
    }


    private static void decodeFile(String coverImagePath){
        byte[] coverImageBytes =  processInput(coverImagePath);
        try {
            OutputStream out = new FileOutputStream("output path");
            out.write(decodeFile(coverImageBytes));
        } catch (IOException e) {
            log.warning("IO exception "  + e );
        }
    }

    private static byte[] decodeFile(byte[] coverImageBytes){
        //TODO
        return new byte[] {};
    }

    private static void encodeFile(String coverImagePath, String inputPath){

        //TODO throw error if input is too big to be hidden in cover image
        byte[] coverImageBytes = processInput(coverImagePath);
        byte[] fileBytes = processInput(inputPath);
        encodeFile(coverImageBytes, fileBytes);
    }

    private static void encodeFile(byte[] coverImage, byte[] input) {
        //start at 54 to skip header of image, split into header and image
        byte[] header = Arrays.copyOfRange(coverImage, 0, 53);
        byte[] image = Arrays.copyOfRange(coverImage, 54, coverImage.length - 1);
        log.info("length of byte array " + image.length);

        //convert Bytes[] to bits
        ArrayList<String> imageBits = new ArrayList();
        for (int x = 0; x < image.length; x++) {
            imageBits.add(Integer.toBinaryString(image[x] & 0xFF));
        }

        ArrayList<String> inputBits = new ArrayList();
        for (int x = 0; x < input.length; x++) {
            inputBits.add(Integer.toBinaryString(input[x] & 0xFF));
        }

//        log.info("image: " + Arrays.toString(image).substring(0, 20));
//        log.info("imageBits: " + imageBits.toString().substring(0,20));
        int inputPosition = 0;
        for (int x = 0; x < imageBits.size(); x++){
            String newValue = imageBits.get(x).substring(0, imageBits.get(x).length() -1) +  inputBits.get(inputPosition);
            imageBits.set(x, newValue);
            inputPosition++;
            if (inputPosition == input.length){
                break;
            }
        }

        //convert bits to Bytes[]
        ArrayList<Byte> newImageBytes = new ArrayList<>();
        inputBits.forEach( b -> newImageBytes.add(Byte.parseByte(b)));

        renderOutputImage(newImageBytes);
    }

    private static void renderOutputImage(ArrayList<Byte> newImageBytes){
        //TODO
    }


    private static byte[] processInput(String path)  {
        Path inputPath = Paths.get(path);
        InputStream inputStream = InputStream.nullInputStream();
        try {
            inputStream = Files.newInputStream(inputPath, StandardOpenOption.CREATE_NEW);
            log.info("processing input stream");
            return inputStream.readAllBytes();
        } catch (IOException e) {
            log.warning("File I/O exception: " + e.toString());
        }
        return new byte[]{};

    }
}
