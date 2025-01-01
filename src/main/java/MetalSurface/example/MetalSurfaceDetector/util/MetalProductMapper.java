package MetalSurface.example.MetalSurfaceDetector.util;

import MetalSurface.example.MetalSurfaceDetector.dto.MetalProductDTO;
import MetalSurface.example.MetalSurfaceDetector.entity.MetalProduct;

import java.util.Base64;

public class MetalProductMapper {

    public static MetalProduct toEntity(MetalProductDTO dto) {
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

    public static MetalProductDTO toDTO(MetalProduct entity) {
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
            return dto;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Error converting MetalProduct to MetalProductDTO: " + e.getMessage(), e);
        }
    }

}
