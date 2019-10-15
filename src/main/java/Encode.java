import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.BitSet;
import java.util.logging.Logger;


public class Encode {
    private static final Logger log = Logger.getLogger(Encode.class.getName());

    public static void main(String[] args) {
        encodeFile("src/main/java/images/all_white_test.bmp", "src/main/java/textfiles/example1.txt");
    }

    private static void encodeFile(String coverImagePath, String inputPath) {
        try {
            byte[] coverImageBytes = InputProcessor.processInput(coverImagePath);
            byte[] fileBytes = InputProcessor.processInput(inputPath);
            encodeFile(coverImageBytes, fileBytes);
        } catch (IOException e) {
            log.warning("IO Exception: " + e.getMessage());
        }
    }

    private static void encodeFile(byte[] coverImage, byte[] input) {
        // start at 54 to skip header of image, split into header and image
        byte[] image = Arrays.copyOfRange(coverImage, 54, coverImage.length);
        byte[] header = Arrays.copyOfRange(coverImage, 0, 54);
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

    private static void renderOutputImage(byte[] newImageBytes) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(newImageBytes);
            BufferedImage bufferedImage = ImageIO.read(bis);
            ImageIO.write(bufferedImage, "bmp", new File("src/main/java/images/all_white_test_new.bmp"));
        } catch (IOException e) {
            log.warning("IO Exception " + e.getMessage());
        }
    }
}
