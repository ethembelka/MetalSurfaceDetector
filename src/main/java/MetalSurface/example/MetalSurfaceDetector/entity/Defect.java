package MetalSurface.example.MetalSurfaceDetector.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "defect")
@Getter
@Setter
public class Defect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "metal_product_id")
    private MetalProduct metalProduct; // İlgili metal ürün

    private String defectType; // Hatanın tipi

    private String coordinates; // Hata koordinatları (örneğin: "x1,y1,x2,y2")

    private Double confidenceRate; // Tahmin oranı

}

