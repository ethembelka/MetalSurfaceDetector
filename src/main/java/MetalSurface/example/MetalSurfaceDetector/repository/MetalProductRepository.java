package MetalSurface.example.MetalSurfaceDetector.repository;

import MetalSurface.example.MetalSurfaceDetector.entity.MetalProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MetalProductRepository extends JpaRepository<MetalProduct, Long>, JpaSpecificationExecutor<MetalProduct> {
}
