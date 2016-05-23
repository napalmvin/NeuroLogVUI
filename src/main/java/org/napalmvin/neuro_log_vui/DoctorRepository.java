package org.napalmvin.neuro_log_vui;

import java.util.List;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	List<Doctor> findByLastNameStartsWithIgnoreCase(String lastName);
}
