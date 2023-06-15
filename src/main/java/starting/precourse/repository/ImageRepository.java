package starting.precourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import starting.precourse.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
