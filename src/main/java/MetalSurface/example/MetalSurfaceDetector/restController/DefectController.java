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
            // DTO'dan Entity'e dönüştürme
            Defect defect = defectService.toEntity(defectDTO);

            // Hata kaydetme işlemi
            Defect savedDefect = defectService.saveDefect(defect);

            // Kaydedilen entity'den DTO oluşturma
            DefectDTO savedDefectDTO = defectService.toDTO(savedDefect);

            return ResponseEntity.ok(savedDefectDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while saving the defect: " + e.getMessage());
        }
    }

}
