package MetalSurface.example.MetalSurfaceDetector.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class MetalProductDTO {

    private Long id;

    private String name; // Ürün ismi

    private String originalImage; // Orijinal metal görüntüsü (Base64 formatında)

    private String processedImage; // Model çıktısından sonraki görüntü (Base64 formatında)

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp; // Görüntü zamanı

    private boolean defective; // Hatalı ürün flagi

    private List<DefectDTO> defectDTOS;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public String getProcessedImage() {
        return processedImage;
    }

    public void setProcessedImage(String processedImage) {
        this.processedImage = processedImage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDefective() {
        return defective;
    }

    public void setDefective(boolean defective) {
        this.defective = defective;
    }

    public List<DefectDTO> getDefectDTOS() {
        return defectDTOS;
    }

    public void setDefectDTOS(List<DefectDTO> defectDTOS) {
        this.defectDTOS = defectDTOS;
    }
}