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
        {
            try {

                byte[] coverImagesBytes = processInput("src/main/java/images/butterfly.bmp");
                byte[] fileBytes = processInput("src/main/java/textfiles/example1.txt");

                encodeFile(coverImagesBytes, fileBytes);

            } catch (IOException e) {
                log.warning("File I/O exception: " + e.toString());
            }
        }
    }

    private static byte[] processInput(String path) throws IOException {
        Path inputPath = Paths.get(path);
        InputStream inputStream = Files.newInputStream(inputPath, StandardOpenOption.CREATE_NEW);

        return inputStream.readAllBytes();
    }

    private static void encodeFile(byte[] coverImage, byte[] input){
        //start at 54 to skip header of image
        for (int x = 54; x < coverImage.length; x = x + 8) {
            //TODO
        }
    }
}
