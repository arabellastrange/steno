import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.logging.Logger;


public class Encode {
    private static final Logger log = Logger.getLogger(Encode.class.getName());
    private static String ENCODED_MESSAGE_PATH;

    public static void encodeFile(String coverImagePath, String inputPath) {
        try {
            ENCODED_MESSAGE_PATH = coverImagePath.replace(".bmp", "_encode.bmp");

            byte[] coverImageBytes = InputProcessor.processInput(coverImagePath);
            byte[] fileBytes = InputProcessor.processInput(inputPath);
            fileBytes = InputProcessor.addTypeAndSize(fileBytes);
            encodeFile(coverImageBytes, fileBytes);
        } catch (IOException e) {
            log.warning("IO Exception: " + e.getMessage());
        }
    }

    private static void encodeFile(byte[] coverImage, byte[] input) {
        // start at 54 to skip header of image, split into header and image
        byte[] image = Arrays.copyOfRange(coverImage, 54, coverImage.length);
        byte[] header = Arrays.copyOfRange(coverImage, 0, 54);

        log.info("First byte of original image header " + header[0]);
        log.info("First byte of image " + image[0]);
        log.info("First byte of input " + input[0]);

        if ((image.length) / 8 > input.length) {
            //convert image byte[] to bits
            BitSet imageBits = byteToBitConverter(image);
            for (int i = 0; i < 8; i++)
                log.info("First 8 bits of image " + imageBits.get(i));

            BitSet inputBits = byteToBitConverter(input);
            for (int i = 0; i < 8; i++)
                log.info("First 8 bits of input " + inputBits.get(i));

            //encode every 8th bit of cover image - corrected
            int inputBitPosition = 0;
            for (int i = 0; i < imageBits.size(); i = i + 8) {
                imageBits.set(i, inputBits.get(inputBitPosition));
                inputBitPosition++;
                if (inputBitPosition == inputBits.length()) {
                    break;
                }
            }

            for (int i = 0; i < 8; i++)
                log.info("First 8 bits of encoded image " + imageBits.get(i));

            //convert bits to byte[]
            byte[] newImageBytes = new byte[header.length + imageBits.length()];

            //concat original header with new encoded image
            System.arraycopy(header, 0, newImageBytes, 0, header.length);
            System.arraycopy(imageBits.toByteArray(), 0, newImageBytes, header.length, imageBits.toByteArray().length);
            log.info("First byte of modified image header " + newImageBytes[0]);

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
            ImageIO.write(bufferedImage, "bmp", new File(ENCODED_MESSAGE_PATH));
        } catch (IOException e) {
            log.warning("IO Exception " + e.getMessage());
        }
    }
}
