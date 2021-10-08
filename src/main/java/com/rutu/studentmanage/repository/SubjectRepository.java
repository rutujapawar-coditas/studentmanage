package com.rutu.studentmanage.repository;

import com.rutu.studentmanage.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Subject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {

    List<Subject> findBySubjectname(String subjectname);
    List<Subject> findBySubjectnameIn(List<String> subjects);
    List<Subject> findBySubjectnameOrBookname(String svalue,String bvalue);
}
