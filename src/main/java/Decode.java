import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.BitSet;
import java.util.logging.Logger;

public class Decode {
    private static final Logger log = Logger.getLogger(Decode.class.getName());
    private static String DECODED_MESSAGE_PATH;
    public static void decodeFile(String encodedImagePath) {
        try {
            //todo change the output file type to the correct type
            DECODED_MESSAGE_PATH = encodedImagePath.replace(".bmp", "_decoded.TXT");
            byte[] encodedImageBytes = InputProcessor.processInput(encodedImagePath);
            decodeFile(encodedImageBytes);
        } catch (IOException e) {
            log.warning("IO Exception: " + e.getMessage());
        }
    }

    private static byte[] decodeFile(byte[] encodedImage) {
        //TODO
        byte[] header = Arrays.copyOfRange(encodedImage, 0, 54);
        byte[] size = Arrays.copyOfRange(encodedImage, 54, 58);
        byte[] ex = Arrays.copyOfRange(encodedImage, 58, 66);

        //int representation of size multiplied by 8 to see how many bytes along need to go
        int sizeInt = ByteBuffer.wrap(size).getInt() * 8;
        log.info("sizeInt: " + sizeInt);
        byte[] message = Arrays.copyOfRange(encodedImage, 66, sizeInt + 66);
        String extension = null;
        try {
            extension = new String(ex, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(extension);

//        File newFile = new File();

        produceOutput(null);
        return new byte[]{};
    }

    private static void produceOutput(byte[] decodedMessage) {
        try (OutputStream out = new FileOutputStream(DECODED_MESSAGE_PATH)) {
            out.write(decodedMessage);
        } catch (IOException e) {
            log.warning("IO Exception " + e.getMessage());
        }
    }
}
