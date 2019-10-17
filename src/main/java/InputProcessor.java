import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.logging.Logger;

public class InputProcessor {
    private static final Logger log = Logger.getLogger(InputProcessor.class.getName());
    private static String fileExtension;

    private InputProcessor() {
    }

    public static byte[] processInput(String path) throws IOException {
        Path inputPath = Paths.get(path);
        InputStream inputStream = InputStream.nullInputStream();
        try {
            fileExtension = path.substring(path.lastIndexOf('.'));
            inputStream = Files.newInputStream(inputPath, StandardOpenOption.CREATE_NEW);
            log.info("processing input stream");
            return inputStream.readAllBytes();
        } finally {
            inputStream.close();
        }
    }

    public static byte[] addTypeAndSize(byte[] bytes) {
        byte[] fileSize = BigInteger.valueOf(bytes.length).toByteArray();

        //pad file size to 4 bytes
        byte[] sizePadding = new byte[]{0, 0, 0, 0};
        byte[] paddedFileSize = Arrays.copyOf(fileSize, 4);
        if (fileSize.length < 4) {
            System.arraycopy(sizePadding, 0, paddedFileSize, 0, 4 - fileSize.length);
            System.arraycopy(fileSize, 0, paddedFileSize, 4 - fileSize.length, fileSize.length);
        }

        byte[] fileType = fileExtension.getBytes(StandardCharsets.UTF_8);

        //pad file extension to 8 bytes
        byte[] typePadding = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
        byte[] paddedFileType = Arrays.copyOf(fileType, 8);
        if(fileType.length < 8){
            System.arraycopy(typePadding, 0, paddedFileType, 0, 8 - fileType.length);
            System.arraycopy(fileType, 0, paddedFileType, 8 - fileType.length, fileType.length);
        }

        byte[] formattedBytes = new byte[bytes.length + 12];
        System.arraycopy(paddedFileSize, 0, formattedBytes, 0, 4);
        System.arraycopy(paddedFileType, 0, formattedBytes, 4, 8);
        System.arraycopy(bytes, 0, formattedBytes, 12, bytes.length);

        return formattedBytes;
    }
}
