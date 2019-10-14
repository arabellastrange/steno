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
import java.util.BitSet;
import java.util.logging.Logger;

public class BitSetStego {
    private final static Logger log = Logger.getLogger(BitSetStego.class.getName());

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
        //TODO add header information to the byte array produced after encoding

        // start at 54 to skip header of image, split into header and image
        byte[] header = Arrays.copyOfRange(coverImage, 0, 53);
        byte[] image = Arrays.copyOfRange(coverImage, 54, coverImage.length - 1);

        log.info("Example byte of image[0]: " + image[0]);
        log.info("Example byte of input [0]: " + input[0]);

        //convert image bytes[] to bits
        BitSet imageBits = byteToBitConverter(image);
        log.info("Example bit of image[0] " + imageBits.get(0));

        BitSet inputBits = byteToBitConverter(input);
        log.info("Example bit of input " + inputBits.get(0));

        int inputBitPosition = 0;
        for (int i = 7; i < imageBits.size(); i = i + 7) {
            imageBits.set(i, inputBits.get(inputBitPosition));
            inputBitPosition++;
        }

        log.info("Example encoded bit of image [0] " + imageBits.get(0));

        //convert bits to Bytes[]
        byte[] newImageBytes = imageBits.toByteArray();

        log.info("Example encoded byte of image[0] " + newImageBytes[0]);

        renderOutputImage(newImageBytes);
    }

    private static BitSet byteToBitConverter(byte[] array) {
        return BitSet.valueOf(array);
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

    private static void renderOutputImage(byte[] newImageBytes) {
        //TODO
    }
}
