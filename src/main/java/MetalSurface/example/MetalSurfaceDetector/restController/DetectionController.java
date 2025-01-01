package MetalSurface.example.MetalSurfaceDetector.restController;

import MetalSurface.example.MetalSurfaceDetector.service.PythonScriptExecutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/detection")
public class DetectionController {

    private final PythonScriptExecutorService pythonScriptExecutorService;

    public DetectionController(PythonScriptExecutorService pythonScriptExecutorService) {
        this.pythonScriptExecutorService = pythonScriptExecutorService;
    }

    @PostMapping("/start")
    public ResponseEntity<Map<String, Object>> startDetection() {
        boolean started;
        String errorMessage = null;

        try {
            started = pythonScriptExecutorService.startDetectionScript();
        } catch (Exception e) {
            // Eğer exception fırlatılırsa, started false olur ve mesajı da hata olarak döndürebiliriz
            started = false;
            errorMessage = "Failed to start detection: " + e.getMessage();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", started);
        response.put("message", started ? "Detection started successfully" : errorMessage != null ? errorMessage : "Failed to start detection");
        response.put("status", started ? "running" : "stopped");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/stop")
    public ResponseEntity<Map<String, Object>> stopDetection() {
        boolean stopped = pythonScriptExecutorService.stopDetectionScript();

        Map<String, Object> response = new HashMap<>();
        response.put("success", stopped);
        response.put("message", stopped ? "Detection stopped successfully" : "Failed to stop detection");
        response.put("status", "stopped");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        boolean isRunning = pythonScriptExecutorService.isScriptRunning();

        Map<String, Object> response = new HashMap<>();
        response.put("status", isRunning ? "running" : "stopped");

        return ResponseEntity.ok(response);
    }

}
