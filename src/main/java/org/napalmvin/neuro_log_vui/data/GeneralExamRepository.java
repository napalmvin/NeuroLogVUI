package org.napalmvin.neuro_log_vui.data;

import java.util.List;
import org.napalmvin.neuro_log_vui.entities.GeneralExam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralExamRepository extends LastNameSearchableRepository<GeneralExam> {
    List<GeneralExam> findByPatientLastNameStartsWithIgnoreCase(String lastName);
    
    @Override
    default List<GeneralExam> findByLastNameStartsWithIgnoreCase(String lastName){
        return findByPatientLastNameStartsWithIgnoreCase(lastName);
    }
}
