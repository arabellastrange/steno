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
        byte[] encodedImage1 = Arrays.copyOfRange(encodedImage, 54, encodedImage.length);
        byte[] ex = Arrays.copyOfRange(encodedImage, 86, 598);

        BitSet imageBitSet = BitSet.valueOf(encodedImage1);
        for (int i = 0; i < 9000; i = i + 8){
            if (i == 432){
                System.out.println("(HEADER)");
            }else if(i == 560){
                System.out.println("(SIZE)");
            }
            else if(i == 1072){
                System.out.println("(EXT)");
            }
            else if(i == 8208){
                System.out.println("(FILE)");
            }
            if (imageBitSet.get(i)){
                System.out.print("1");
            }else{
                System.out.print("0");
            }
        }
        BitSet decodedSize = new BitSet();
        for (int i = 432; i < 560; i = i + 8) {
            decodedSize.set(i, imageBitSet.get(i));
        }


        byte[] finalSize = decodedSize.toByteArray();
        int sizeInt = ByteBuffer.wrap(finalSize).getInt() * 8;
        log.info("sizeInt: " + sizeInt);


//        try {
//            wholetetx = new String(encodedImage, "UTF-8");
//            System.out.println(wholetetx);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        byte[] message = Arrays.copyOfRange(encodedImage, 66, sizeInt + 66);
//        String extension = null;
//        try {
//            extension = new String(ex, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(extension);

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
