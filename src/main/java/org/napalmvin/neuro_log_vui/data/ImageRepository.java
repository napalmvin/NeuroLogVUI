package org.napalmvin.neuro_log_vui.data;

import java.util.List;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.napalmvin.neuro_log_vui.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {

	List<Doctor> findByPathContainsIgnoreCase(String path);
}
