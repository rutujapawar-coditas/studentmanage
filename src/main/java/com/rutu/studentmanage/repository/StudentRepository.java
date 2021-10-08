package com.rutu.studentmanage.repository;

import com.rutu.studentmanage.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    // query for student having rollno in range(eg-2 -4)
    public List<Student> findByRollnoBetween(Integer start, Integer end);


    // join query for student having subject english and maths
    @Query( value = "select * from student s " +
        "join subject t " +
        "   on s.id = t.student_id "  +
        "where t.subjectname in :subjectname", nativeQuery = true)
    public List<Student> findBySubjectname(@Param("subjectname")List<String> subjects);
}
