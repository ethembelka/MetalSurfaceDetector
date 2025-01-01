package MetalSurface.example.MetalSurfaceDetector.repository;

import MetalSurface.example.MetalSurfaceDetector.entity.Defect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefectRepository extends JpaRepository<Defect, Long> {

    List<Defect> getDefectsByMetalProductId(Long metalProductId);
}
