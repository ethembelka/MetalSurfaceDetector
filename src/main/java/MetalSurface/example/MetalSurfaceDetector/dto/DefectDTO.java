package MetalSurface.example.MetalSurfaceDetector.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefectDTO {
    private Long id;
    private Long metalProductId; // MetalProduct ID'si
    private String defectType; // Hatanın tipi
    private String coordinates; // Hata koordinatları
    private Double confidenceRate; // Tahmin oranı

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
