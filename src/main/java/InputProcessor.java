import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;

public class InputProcessor {
    private static final Logger log = Logger.getLogger(InputProcessor.class.getName());

    private InputProcessor() {
    }

    public static byte[] processInput(String path) throws IOException {
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

    public static byte[] addDelimiter(byte[] bytes) {
        byte[] delimiter = new byte[]{Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE};
        byte[] formattedBytes = new byte[bytes.length + delimiter.length];
        System.arraycopy(delimiter, 0, formattedBytes, 0, delimiter.length);
        System.arraycopy(bytes, 0, formattedBytes, 3, bytes.length);

        return formattedBytes;
    }

    public static byte[] addTypeAndSize(byte[] bytes) {
        return bytes;
    }
}
