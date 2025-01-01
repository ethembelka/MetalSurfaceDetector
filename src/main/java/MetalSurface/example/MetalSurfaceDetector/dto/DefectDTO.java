package MetalSurface.example.MetalSurfaceDetector.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefectDTO {
    private Long id;
    private Long metalProductId;
    private String defectType;
    private String coordinates;
    private Double confidenceRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMetalProductId() {
        return metalProductId;
    }

    public void setMetalProductId(Long metalProductId) {
        this.metalProductId = metalProductId;
    }

    public String getDefectType() {
        return defectType;
    }

    public void setDefectType(String defectType) {
        this.defectType = defectType;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Double getConfidenceRate() {
        return confidenceRate;
    }

    public void setConfidenceRate(Double confidenceRate) {
        this.confidenceRate = confidenceRate;
    }
}
