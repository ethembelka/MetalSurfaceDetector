package MetalSurface.example.MetalSurfaceDetector.restController;

import MetalSurface.example.MetalSurfaceDetector.dto.DefectDTO;
import MetalSurface.example.MetalSurfaceDetector.entity.Defect;
import MetalSurface.example.MetalSurfaceDetector.service.DefectService;
import MetalSurface.example.MetalSurfaceDetector.util.DefectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/defects")
public class DefectController {

    private final DefectService defectService;

    public DefectController(DefectService defectService) {
        this.defectService = defectService;
    }

    @PostMapping
    public ResponseEntity<?> createDefect(@RequestBody DefectDTO defectDTO) {
        try {
            Defect defect = defectService.toEntity(defectDTO);

            Defect savedDefect = defectService.saveDefect(defect);

            DefectDTO savedDefectDTO = defectService.toDTO(savedDefect);

            return ResponseEntity.ok(savedDefectDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while saving the defect: " + e.getMessage());
        }
    }

}
