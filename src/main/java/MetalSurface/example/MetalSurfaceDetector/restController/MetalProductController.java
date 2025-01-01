package MetalSurface.example.MetalSurfaceDetector.restController;

import MetalSurface.example.MetalSurfaceDetector.config.WebSocketService;
import MetalSurface.example.MetalSurfaceDetector.dto.DefectDTO;
import MetalSurface.example.MetalSurfaceDetector.dto.MetalProductDTO;
import MetalSurface.example.MetalSurfaceDetector.dto.MetalProductFilterDTO;
import MetalSurface.example.MetalSurfaceDetector.entity.Defect;
import MetalSurface.example.MetalSurfaceDetector.entity.MetalProduct;
import MetalSurface.example.MetalSurfaceDetector.service.DefectService;
import MetalSurface.example.MetalSurfaceDetector.service.MetalProductService;
import MetalSurface.example.MetalSurfaceDetector.util.MetalProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/metal-products")
public class MetalProductController {

    private final MetalProductService metalProductService;

    private final DefectService defectService;

    private final WebSocketService webSocketService;


    public MetalProductController(MetalProductService metalProductService,
                                  WebSocketService webSocketService, DefectService defectService) {
        this.metalProductService = metalProductService;
        this.webSocketService = webSocketService;
        this.defectService = defectService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMetalProduct(@RequestBody MetalProductDTO productDTO) {
        try {

            // DTO'dan Entity'ye dönüştürme
            MetalProduct product = metalProductService.toEntity(productDTO);

            // Servis çağrısı
            MetalProduct savedProduct = metalProductService.saveMetalProduct(product);

            if (productDTO.getDefectDTOS() != null && !productDTO.getDefectDTOS().isEmpty()) {
                for (DefectDTO defectDTO: productDTO.getDefectDTOS()) {
                    defectDTO.setMetalProductId(savedProduct.getId());
                    Defect defect = defectService.toEntity(defectDTO);
                    defectService.saveDefect(defect);
                }
            }

            // Entity'den DTO'ya dönüştürme
            MetalProductDTO savedProductDTO = metalProductService.toDTO(savedProduct);

            webSocketService.sendMetalProduct(savedProductDTO);

            return ResponseEntity.ok(savedProductDTO);
        } catch (IllegalArgumentException e) {
            // Dönüştürme sırasında hata oluşursa
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            // Diğer beklenmeyen hatalar
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<MetalProductDTO>> getAllDataByFilter(
            @RequestBody MetalProductFilterDTO filterDTO) {
        return ResponseEntity.ok(metalProductService.filterMetalProducts(filterDTO));
    }


    @GetMapping
    public ResponseEntity<?> getAllMetalProducts() {
        try {
            // Tüm ürünleri servisten al
            List<MetalProduct> products = metalProductService.getAllMetalProducts();

            // Entity'leri DTO'lara dönüştür
            List<MetalProductDTO> productDTOs = products.stream()
                    .map(MetalProductMapper::toDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(productDTOs);
        } catch (Exception e) {
            // Beklenmeyen hataları ele al
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving metal products: " + e.getMessage());
        }
    }


}
