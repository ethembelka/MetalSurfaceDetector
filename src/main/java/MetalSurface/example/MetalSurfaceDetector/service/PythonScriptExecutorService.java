package MetalSurface.example.MetalSurfaceDetector.service;

import MetalSurface.example.MetalSurfaceDetector.config.WebSocketService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PythonScriptExecutorService {
    private static final Logger logger = LoggerFactory.getLogger(PythonScriptExecutorService.class);

    private final WebSocketService webSocketService;

    @Value("${python.script.path}")
    private String pythonScriptPath;

    @Value("${python.executable.path}")
    private String pythonExecutablePath;

    private Process currentProcess;
    private boolean isRunning = false;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public PythonScriptExecutorService(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    public synchronized boolean startDetectionScript() {
        if (isRunning) {
            return false;
        }

        try {
            File scriptFile = new File(pythonScriptPath);
            if (!scriptFile.exists()) {
                throw new FileNotFoundException("Python script not found at: " + pythonScriptPath);
            }

            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("C:/Users/ASUS/anaconda3/Scripts/activate.bat", "C:/Users/ASUS/anaconda3/envs/metal_surface_yolov8", "&&", "python", pythonScriptPath);
            //processBuilder.command(pythonExecutablePath, pythonScriptPath);
            processBuilder.redirectErrorStream(true);

            currentProcess = processBuilder.start();
            isRunning = true;

            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(currentProcess.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null && isRunning) {
                        logger.info("Python output: " + line);
                        sendStatusUpdate("running", line);
                    }
                } catch (IOException e) {
                    logger.error("Error reading Python output", e);
                    sendStatusUpdate("error", "Error reading Python output: " + e.getMessage());
                }
            }).start();

            new Thread(() -> {
                try {
                    int exitCode = currentProcess.waitFor();
                    if (exitCode != 0) {
                        logger.error("Python script exited with code: " + exitCode);
                        sendStatusUpdate("error", "Script exited with code: " + exitCode);
                    }
                    isRunning = false;
                    sendStatusUpdate("stopped", "Detection stopped");
                } catch (InterruptedException e) {
                    logger.error("Process interrupted", e);
                    sendStatusUpdate("error", "Process interrupted: " + e.getMessage());
                }
            }).start();

            sendStatusUpdate("started", "Detection started successfully");
            return true;

        } catch (IOException e) {
            logger.error("Error starting Python script", e);
            sendStatusUpdate("error", "Failed to start detection: " + e.getMessage());
            isRunning = false;
            return false;
        } catch (Exception e) {
            logger.error("Error starting Python script", e);
            sendStatusUpdate("error", "General error starting detection: " + e.getMessage());
            isRunning = false;
            return false;
        }
    }

    public synchronized boolean stopDetectionScript() {
        if (!isRunning || currentProcess == null) {
            return false;
        }

        try {
            isRunning = false;
            currentProcess.destroy();

            // Grace period for normal termination
            if (!currentProcess.waitFor(5, TimeUnit.SECONDS)) {
                currentProcess.destroyForcibly();
            }

            sendStatusUpdate("stopped", "Detection stopped successfully");
            return true;
        } catch (Exception e) {
            logger.error("Error stopping Python script", e);
            sendStatusUpdate("error", "Failed to stop detection: " + e.getMessage());
            return false;
        }

    }

    public boolean isScriptRunning() {
        return isRunning;
    }

    private void sendStatusUpdate(String status, String message) {
        Map<String, Object> update = new HashMap<>();
        update.put("status", status);
        update.put("message", message);
        update.put("timestamp", LocalDateTime.now().toString());

        webSocketService.sendDetectionStatus(update);
    }

}
