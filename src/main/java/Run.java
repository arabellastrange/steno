import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;
import java.util.Arrays;
import java.util.logging.Logger;

public class Run {
    private final static Logger log = Logger.getLogger(Run.class.getName());

    public static void main(String[] args) {
        {
            try {
                Path coverImagePath = Paths.get("src/main/java/images/butterfly.bmp");
                Path filePath = Paths.get("src/main/java/textfiles/example1.txt");

                InputStream coverInputStream = Files.newInputStream(coverImagePath, StandardOpenOption.CREATE_NEW);
                InputStream fileInputStream = Files.newInputStream(filePath, StandardOpenOption.CREATE_NEW);

                byte[] coverImagesBytes = coverInputStream.readAllBytes();
                byte[] fileBytes = fileInputStream.readAllBytes();
                byte aByte  = fileBytes[0];
                // testing byte conversion

                log.info("a byte " +  aByte + " a byte as unsigned int is: " + Byte.toUnsignedInt(aByte));

                //start at 54 to skip header of image
                for (int x = 54; x < coverImagesBytes.length; x = x + 8 ){

                }
            } catch (IOException e) {
                log.warning("File i/o error, exception " +  e.toString());
            }
        }
    }
}
