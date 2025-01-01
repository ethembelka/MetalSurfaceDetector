package MetalSurface.example.MetalSurfaceDetector.service;

import MetalSurface.example.MetalSurfaceDetector.dto.DefectDTO;
import MetalSurface.example.MetalSurfaceDetector.dto.MetalProductDTO;
import MetalSurface.example.MetalSurfaceDetector.dto.MetalProductFilterDTO;
import MetalSurface.example.MetalSurfaceDetector.entity.MetalProduct;
import MetalSurface.example.MetalSurfaceDetector.entity.MetalProductSpecification;
import MetalSurface.example.MetalSurfaceDetector.repository.MetalProductRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetalProductService {

    private final MetalProductRepository metalProductRepository;
    private final MetalProductSpecification metalProductSpecification;

    private final DefectService defectService;

    public MetalProductService(MetalProductRepository metalProductRepository, DefectService defectService,
                               MetalProductSpecification metalProductSpecification) {
        this.metalProductRepository = metalProductRepository;
        this.defectService = defectService;
        this.metalProductSpecification = metalProductSpecification;
    }

    public MetalProduct saveMetalProduct(MetalProduct product) {
        return metalProductRepository.save(product);
    }

    public List<MetalProduct> getAllMetalProducts() {
        return metalProductRepository.findAll();
    }

    public MetalProduct toEntity(MetalProductDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("MetalProductDTO cannot be null");
        }

        try {
            MetalProduct product = new MetalProduct();
            product.setName(dto.getName());
            product.setOriginalImage(Base64.getDecoder().decode(dto.getOriginalImage()));
            product.setProcessedImage(Base64.getDecoder().decode(dto.getProcessedImage()));
            product.setTimestamp(dto.getTimestamp());
            product.setDefective(dto.isDefective());
            return product;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Error converting MetalProductDTO to MetalProduct: " + e.getMessage(), e);
        }
    }

    public MetalProductDTO toDTO(MetalProduct entity) {
        if (entity == null) {
            throw new IllegalArgumentException("MetalProduct cannot be null");
        }

        try {
            MetalProductDTO dto = new MetalProductDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setOriginalImage(Base64.getEncoder().encodeToString(entity.getOriginalImage()));
            dto.setProcessedImage(Base64.getEncoder().encodeToString(entity.getProcessedImage()));
            dto.setTimestamp(entity.getTimestamp());
            dto.setDefective(entity.isDefective());
            List<DefectDTO> defectDTOS = defectService.getDefectDtosByMetalProductId(entity.getId());
            dto.setDefectDTOS(defectDTOS);
            return dto;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Error converting MetalProduct to MetalProductDTO: " + e.getMessage(), e);
        }
    }

    public Page<MetalProductDTO> filterMetalProducts(MetalProductFilterDTO filterDTO) {
        Sort.Direction direction = filterDTO.getSortDirection().equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(
                filterDTO.getPage(),
                filterDTO.getSize(),
                Sort.by(direction, filterDTO.getSortBy())
        );

        Specification<MetalProduct> spec = metalProductSpecification
                .createSpecification(filterDTO);

        Page<MetalProduct> entityPage = metalProductRepository.findAll(spec, pageable);

        List<MetalProductDTO> dtos = entityPage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }



}
