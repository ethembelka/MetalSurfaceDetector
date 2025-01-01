package MetalSurface.example.MetalSurfaceDetector.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "metal-product")
public class MetalProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] originalImage; // Orijinal metal görüntüsü

    @Lob
    private byte[] processedImage; // Model çıktısından sonraki görüntü

    private String name; // Ürün ismi

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp; // Görüntü zamanı

    private boolean defective; // Hatalı ürün flagi

    @OneToMany(mappedBy = "metalProduct")
    private List<Defect> defects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(byte[] originalImage) {
        this.originalImage = originalImage;
    }

    public byte[] getProcessedImage() {
        return processedImage;
    }

    public void setProcessedImage(byte[] processedImage) {
        this.processedImage = processedImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Defect> getDefects() {
        return defects;
    }

    public void setDefects(List<Defect> defects) {
        this.defects = defects;
    }
}

