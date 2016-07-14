package org.napalmvin.neuro_log_vui.data;

import java.util.List;
import org.napalmvin.neuro_log_vui.entities.GeneralExam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralExamRepository extends JpaRepository<GeneralExam, Long> {
    List<GeneralExam> findByPatientLastNameStartsWithIgnoreCase(String lastName);
}
