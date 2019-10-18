import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.logging.Logger;

class Decode {
    private static final Logger log;
    private static String DECODED_MESSAGE_PATH;
    private static String fileExtension;

    static {
        String path = Decode.class.getClassLoader()
                .getResource("logging.properties")
                .getFile();
        System.setProperty("java.util.logging.config.file", path);
        log = Logger.getLogger(Decode.class.getName());
    }

    private Decode() {
    }

    public static void decodeFile(String encodedImagePath) {
        try {
            byte[] encodedImageBytes = InputProcessor.processInput(encodedImagePath);
            byte[] output = decodeFile(encodedImageBytes);
            DECODED_MESSAGE_PATH = encodedImagePath.replace("src/main/java/images/", "src/main/java/output/");
            DECODED_MESSAGE_PATH = DECODED_MESSAGE_PATH.replace(".bmp", "_decoded" + fileExtension);
            log.info("Saving output to: " + DECODED_MESSAGE_PATH);
            produceOutput(output);
        } catch (IOException e) {
            log.warning("IO Exception: " + e.getMessage());
        }
    }

    private static byte[] decodeFile(byte[] encodedImage) {
        //ignore image header
        byte[] headlessEncodedImage = Arrays.copyOfRange(encodedImage, 54, encodedImage.length);
        //32 bytes store 32 bits of file size information
        byte[] fileSize = Arrays.copyOfRange(headlessEncodedImage, 0, 32);
        //64 bytes store 64 bits of file type information
        byte[] extension = Arrays.copyOfRange(headlessEncodedImage, 32, 96);

        BitSet lsbSize = extractLSBFromByteArray(fileSize);
        BitSet lsbExtension = extractLSBFromByteArray(extension);

        // convert byte length to bit length, add the 12 bytes at the end that are used for the ext and size
        int fileBitLength = (ByteBuffer.wrap(lsbSize.toByteArray()).getInt() * 8) + 96;
        fileExtension = new String(lsbExtension.toByteArray());
        fileExtension = fileExtension.substring(fileExtension.indexOf('.'));

        // remaining encoded bytes
        byte[] message = Arrays.copyOfRange(headlessEncodedImage, 96, fileBitLength);
        BitSet lsbMessage = extractLSBFromByteArray(message);
        byte[] encodedMessage = lsbMessage.toByteArray();

        log.info("File size: " + fileBitLength);
        log.info("File extension is:" + fileExtension);

        return encodedMessage;
    }

    private static BitSet extractLSBFromByteArray(byte[] bytes) {
        BitSet byteSet = BitSet.valueOf(bytes);
        BitSet lsbSet = new BitSet();
        int n = 0;

        for (int i = 0; i < byteSet.length(); i = i + 8) {
            lsbSet.set(n, byteSet.get(i));
            n++;
        }
        return lsbSet;
    }

    private static void produceOutput(byte[] decodedMessage) {
        try (OutputStream out = new FileOutputStream(DECODED_MESSAGE_PATH)) {
            out.write(decodedMessage);
        } catch (IOException e) {
            log.warning("IO Exception " + e.getMessage());
        }
    }
}
