import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.BitSet;
import java.util.logging.Logger;


public class BitSetStego {
    private static final Logger log = Logger.getLogger(BitSetStego.class.getName());

    public static void main(String[] args) {
        encodeFile("src/main/java/images/all_white_test.bmp", "src/main/java/textfiles/example1.txt");
    }

    private static void decodeFile(String coverImagePath) throws IOException {
        byte[] coverImageBytes = processInput(coverImagePath);
        OutputStream out = OutputStream.nullOutputStream();
        try {
            out = new FileOutputStream("output path");
            out.write(decodeFile(coverImageBytes));
        } finally {
            out.close();
        }
    }

    private static byte[] decodeFile(byte[] coverImageBytes) {
        //TODO
        return new byte[]{};
    }

    private static void encodeFile(String coverImagePath, String inputPath) {
        try {
            byte[] coverImageBytes = processInput(coverImagePath);
            byte[] fileBytes = processInput(inputPath);
            encodeFile(coverImageBytes, fileBytes);
        } catch (IOException e) {
            log.warning("IO Exception: " + e.toString());
        }
    }

    private static void encodeFile(byte[] coverImage, byte[] input) {
        // start at 54 to skip header of image, split into header and image
        byte[] image = Arrays.copyOfRange(coverImage, 54, coverImage.length);
        byte[] header = Arrays.copyOfRange(coverImage, 0, 54);
        log.info("Original image header " + header[0]);
        log.info("Example byte of image[0]: " + image[0]);
        log.info("Example byte of input [0]: " + input[0]);

        if (image.length > input.length) {
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

    private static byte[] processInput(String path) throws IOException {
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

    private static void renderOutputImage(byte[] newImageBytes) {
        //TODO
//        log.info("newImageBytes: " + Arrays.toString(newImageBytes));
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(newImageBytes);
            BufferedImage bufferedImage = ImageIO.read(bis);
            ImageIO.write(bufferedImage, "bmp", new File("src/main/java/images/all_white_test_new.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        //bytes 18-21 represent width. 22-25 represent height
//        byte[] widthArray = Arrays.copyOfRange(newImageBytes, 18, 22);
//        byte[] heightArray = Arrays.copyOfRange(newImageBytes, 22, 26);
//
//        log.info("start of array: " +  Arrays.toString(Arrays.copyOfRange(newImageBytes, 0, 27)));
//        log.info("widthArray: " + Arrays.toString(widthArray));
//        log.info("heightArray: " + Arrays.toString(heightArray));
//        for ( int x = 0; x < widthArray.length; x++) {
//            log.info(widthArray[x]);
//        }
//        log.info("heightArray: " + heightArray.toString());
//        int width;
//        int height;
//        BufferedImage outputImage = new BufferedImage(newImageBytes.get(18), newImageBytes[22], );
//
//        try {
//            ImageIO.write(outputImage, "BMP", new File("src/main/java/images/output.bmp"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try
//        {
//            File ms = new File();
//            ms.Write(byteArrayIn, 0, byteArrayIn.Length);
//            returnImage = Image.FromStream(ms,true);//Exception occurs here
//        }
//        catch { }
    }
}
