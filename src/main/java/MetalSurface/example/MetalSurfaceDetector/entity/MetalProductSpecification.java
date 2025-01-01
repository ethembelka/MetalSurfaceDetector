package MetalSurface.example.MetalSurfaceDetector.entity;

import MetalSurface.example.MetalSurfaceDetector.dto.MetalProductFilterDTO;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MetalProductSpecification {

    public Specification<MetalProduct> createSpecification(MetalProductFilterDTO filterDTO) {
        return Specification
                .where(nameContains(filterDTO.getName()))
                .and(isDefective(filterDTO.getDefective()))
                .and(hasDefectType(filterDTO.getDefectType()))
                .and(hasConfidenceRate(filterDTO.getConfidenceRate()))
                .and(isWithinDateRange(filterDTO.getStartDate(), filterDTO.getEndDate()));
    }

    private Specification<MetalProduct> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }
            return cb.like(cb.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%");
        };
    }

    private Specification<MetalProduct> isDefective(Boolean defective) {
        return (root, query, cb) -> {
            if (defective == null) {
                return null;
            }
            return cb.equal(root.get("defective"), defective);
        };
    }

    private Specification<MetalProduct> hasDefectType(String defectType) {
        return (root, query, cb) -> {
            if (defectType == null || defectType.trim().isEmpty()) {
                return null;
            }
            Join<MetalProduct, Defect> defectJoin = root.join("defects", JoinType.LEFT);
            return cb.equal(defectJoin.get("defectType"), defectType);
        };
    }

    private Specification<MetalProduct> hasConfidenceRate(Double confidenceRate) {
        return (root, query, cb) -> {
            if (confidenceRate == null) {
                return null;
            }
            Join<MetalProduct, Defect> defectJoin = root.join("defects", JoinType.LEFT);
            return cb.greaterThanOrEqualTo(defectJoin.get("confidenceRate"),
                    confidenceRate);
        };
    }

    private Specification<MetalProduct> isWithinDateRange(
            LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null && endDate == null) {
                return null;
            }

            if (startDate != null && endDate != null) {
                return cb.between(root.get("timestamp"), startDate, endDate);
            }

            if (startDate != null) {
                return cb.greaterThanOrEqualTo(root.get("timestamp"), startDate);
            }

            return cb.lessThanOrEqualTo(root.get("timestamp"), endDate);
        };
    }
}
