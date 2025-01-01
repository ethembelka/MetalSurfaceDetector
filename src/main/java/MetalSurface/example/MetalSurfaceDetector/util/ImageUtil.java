package MetalSurface.example.MetalSurfaceDetector.util;

import java.util.Base64;

public class ImageUtil {

    public static String encodeToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public static byte[] decodeFromBase64(String base64Image) {
        return Base64.getDecoder().decode(base64Image);
    }
}

