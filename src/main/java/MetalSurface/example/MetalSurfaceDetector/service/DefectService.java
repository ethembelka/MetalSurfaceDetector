package MetalSurface.example.MetalSurfaceDetector.service;

import MetalSurface.example.MetalSurfaceDetector.dto.DefectDTO;
import MetalSurface.example.MetalSurfaceDetector.entity.Defect;
import MetalSurface.example.MetalSurfaceDetector.entity.MetalProduct;
import MetalSurface.example.MetalSurfaceDetector.repository.DefectRepository;
import MetalSurface.example.MetalSurfaceDetector.repository.MetalProductRepository;
import MetalSurface.example.MetalSurfaceDetector.util.DefectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefectService {

    private final DefectRepository defectRepository;
    private final MetalProductRepository metalProductRepository;

    public DefectService(DefectRepository defectRepository, MetalProductRepository metalProductRepository) {
        this.defectRepository = defectRepository;
        this.metalProductRepository = metalProductRepository;
    }

    public Defect saveDefect(Defect defect) {
        return defectRepository.save(defect);
    }

    public Defect toEntity(DefectDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DefectDTO is null and cannot be mapped to entity");
        }

        MetalProduct metalProduct = null;
        if (dto.getMetalProductId() != null) {
            metalProduct = metalProductRepository.findById(dto.getMetalProductId())
                    .orElseThrow(() -> new RuntimeException("MetalProduct not found with id: " + dto.getMetalProductId()));
        }

        return Defect.builder()
                .id(dto.getId())
                .metalProduct(metalProduct) // MetalProduct burada setleniyor
                .defectType(dto.getDefectType())
                .coordinates(dto.getCoordinates())
                .confidenceRate(dto.getConfidenceRate())
                .build();
    }

    public DefectDTO toDTO(Defect entity) {
        try {
            if (entity == null) {
                throw new IllegalArgumentException("Defect entity is null and cannot be mapped to DTO");
            }

            return DefectDTO.builder()
                    .id(entity.getId())
                    .metalProductId(entity.getMetalProduct() != null ? entity.getMetalProduct().getId() : null)
                    .defectType(entity.getDefectType())
                    .coordinates(entity.getCoordinates())
                    .confidenceRate(entity.getConfidenceRate())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to map Defect entity to DefectDTO", e);
        }
    }

    public List<DefectDTO> getDefectDtosByMetalProductId(Long metalProductId) {
        List<Defect> defects = defectRepository.getDefectsByMetalProductId(metalProductId);

        // DefectDTO nesnelerini oluşturmak için bir liste başlat
        List<DefectDTO> defectDTOs = new ArrayList<>();

        // Her bir Defect varlığını DefectDTO'ya dönüştür
        for (Defect defect : defects) {
            DefectDTO defectDTO = toDTO(defect);
            defectDTOs.add(defectDTO);
        }

        return defectDTOs;

    }

}
