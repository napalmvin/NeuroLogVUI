package org.napalmvin.neuro_log_vui.data;

import java.util.List;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.napalmvin.neuro_log_vui.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	List<Doctor> findByLastNameStartsWithIgnoreCase(String lastName);
}
