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
    private MetalProduct metalProduct;

    private String defectType;

    private String coordinates;

    private Double confidenceRate;

}

