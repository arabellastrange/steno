import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class Run {
    private final static Logger log = Logger.getLogger(Run.class.getName());

    public static void main(String[] args) {
        encodeFile("src/main/java/images/butterfly.bmp", "src/main/java/textfiles/example1.txt");
    }

    private static void decodeFile(String coverImagePath) {
        byte[] coverImageBytes = processInput(coverImagePath);
        try {
            OutputStream out = new FileOutputStream("output path");
            out.write(decodeFile(coverImageBytes));
        } catch (IOException e) {
            log.warning("IO exception " + e);
        }
    }

    private static byte[] decodeFile(byte[] coverImageBytes) {
        //TODO
        return new byte[]{};
    }

    private static void encodeFile(String coverImagePath, String inputPath) {
        //TODO throw error if input is too big to be hidden in cover image
        byte[] coverImageBytes = processInput(coverImagePath);
        byte[] fileBytes = processInput(inputPath);
        encodeFile(coverImageBytes, fileBytes);
    }

    private static void encodeFile(byte[] coverImage, byte[] input) {
        //start at 54 to skip header of image, split into header and image
        //TODO add header information to the byte array produced after encoding
        byte[] header = Arrays.copyOfRange(coverImage, 0, 53);
        byte[] image = Arrays.copyOfRange(coverImage, 54, coverImage.length - 1);

        log.info("Example byte of image[0]: " + image[0]);
        log.info("Example byte of input [0]: " + input[0]);

        //convert image bytes[] to bits
        ArrayList<String> imageBits = new ArrayList();
        for (int x = 0; x < image.length; x++) {
            imageBits.add(Integer.toBinaryString(image[x] & 0xFF));
        }

        log.info("Example bit of image[0] " + imageBits.get(0));

        //convert input bytes[] to bits
        ArrayList<String> inputBits = new ArrayList();
        for (int x = 0; x < input.length; x++) {
            String inputBit = Integer.toBinaryString(input[x] & 0xFF);
            //pad input bits with zeros to produce fixed length bytes to encode
            if (inputBit.length() < 8) {
                int padding = 8 - inputBit.length();
                for (int i = 0; i < padding; i++) {
                    inputBit = "0" + inputBit;
                }
            }
            inputBits.add(inputBit);
        }
        log.info("Example bit of input " + inputBits.get(0));

        int inputBytePosition = 0;
        int inputBitPosition = 0;
        for (int x = 0; x < imageBits.size(); x++) {
            String newValue = imageBits.get(x).substring(0, imageBits.get(x).length() - 1) + inputBits.get(inputBytePosition).substring(inputBitPosition, inputBitPosition + 1);
            imageBits.set(x, newValue);

            inputBitPosition++;

            if(inputBitPosition == 7){
               inputBytePosition++;
               inputBitPosition = 0;
            }

            if (inputBytePosition == input.length) {
                break;
            }
        }

        log.info("Example encoded bit of image [0] " + imageBits.get(0));

        //convert bits to Bytes[]
        ArrayList<Byte> newImageBytes = new ArrayList<>();
        imageBits.forEach(b -> newImageBytes.add(Byte.parseByte(b, 2)));

        log.info("Example encoded byte of image[0] " + newImageBytes.get(0));

        renderOutputImage(newImageBytes);
    }

    private static void renderOutputImage(ArrayList<Byte> newImageBytes) {
        //TODO
    }


    private static byte[] processInput(String path) {
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
