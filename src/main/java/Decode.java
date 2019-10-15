import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

public class Decode {
    private static final Logger log = Logger.getLogger(Decode.class.getName());

    public static void main(String[] args) {
        decodeFile("");
    }

    private static void decodeFile(String coverImagePath) {
        try {
            byte[] coverImageBytes = InputProcessor.processInput(coverImagePath);
            decodeFile(coverImageBytes);
        } catch (IOException e) {
            log.warning("IO Exception: " + e.getMessage());
        }
    }

    private static byte[] decodeFile(byte[] coverImageBytes) {
        //TODO
        produceOutput(null);
        return new byte[]{};
    }

    private static void produceOutput(byte[] decodedMessage) {
        try (OutputStream out = new FileOutputStream("output path")) {
            out.write(decodedMessage);
        } catch (IOException e) {
            log.warning("IO Exception " + e.getMessage());
        }
    }
}
