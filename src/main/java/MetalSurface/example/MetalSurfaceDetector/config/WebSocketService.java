package MetalSurface.example.MetalSurfaceDetector.config;

import MetalSurface.example.MetalSurfaceDetector.dto.MetalProductDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMetalProduct(MetalProductDTO metalProductDTO) {
        messagingTemplate.convertAndSend("/topic/metal-products", metalProductDTO);
    }

    public void sendDetectionStatus(Map<String, Object> updateObject) {
        messagingTemplate.convertAndSend("/topic/detection-status", updateObject);
    }

}
