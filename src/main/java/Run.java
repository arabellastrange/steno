import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;

public class Run {
    private final static Logger log = Logger.getLogger(Run.class.getName());

    public static void main(String[] args) {

        byte[] coverImagesBytes = processInput("src/main/java/images/butterfly.bmp");
        byte[] fileBytes = processInput("src/main/java/textfiles/example1.txt");

        encodeFile(coverImagesBytes, fileBytes);

    }

    private static byte[] processInput(String path)  {
        Path inputPath = Paths.get(path);
        InputStream inputStream = InputStream.nullInputStream();
        try {
            inputStream = Files.newInputStream(inputPath, StandardOpenOption.CREATE_NEW);
            inputStream.close();
            return inputStream.readAllBytes();
        } catch (IOException e) {
            log.warning("File I/O exception: " + e.toString());
        }
        return new byte[]{};

    }

    private static void encodeFile(byte[] coverImage, byte[] input) {
        //start at 54 to skip header of image
        for (int x = 54; x < coverImage.length; x = x + 8) {
            //TODO
        }
    }
}
