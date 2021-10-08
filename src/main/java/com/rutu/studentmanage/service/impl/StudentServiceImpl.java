package com.rutu.studentmanage.service.impl;

import com.rutu.studentmanage.domain.Student;
import com.rutu.studentmanage.repository.StudentRepository;
import com.rutu.studentmanage.service.StudentService;
import com.rutu.studentmanage.service.dto.StudentDTO;
import com.rutu.studentmanage.service.mapper.StudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Student}.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        log.debug("Request to save Student : {}", studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Override
    public Optional<StudentDTO> partialUpdate(StudentDTO studentDTO) {
        log.debug("Request to partially update Student : {}", studentDTO);

        return studentRepository
            .findById(studentDTO.getId())
            .map(
                existingStudent -> {
                    studentMapper.partialUpdate(existingStudent, studentDTO);

                    return existingStudent;
                }
            )
            .map(studentRepository::save)
            .map(studentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Students");
        return studentRepository.findAll(pageable).map(studentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentDTO> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id).map(studentMapper::toDto);
    }

    //retrieve Students having rollno Between range( eg- 2 to 5)
    @Override
    @Transactional(readOnly = true)
    public List<StudentDTO> findTwo(Integer firstvalue, Integer secondvalue) {
        log.debug("Request to get Student : {}", firstvalue,secondvalue);
        List<Student> studentlist= studentRepository.findByRollnoBetween(firstvalue,secondvalue);
        return studentlist.stream().map(studentMapper::toDto).collect(Collectors.toList());
    }


    //retrieve Students having subject as english or maths using join query
    @Override
    @Transactional(readOnly = true)
    public List<StudentDTO> findThree(String subjectname) {
        log.debug("Request to get Student : {}", subjectname);
        List<String> subject1=new ArrayList<>();
        subject1.add("English");
        subject1.add("Maths");
        List<Student> studentlist1= studentRepository.findBySubjectname(subject1);
        return studentlist1.stream().map(studentMapper::toDto).collect(Collectors.toList());
    }

    //retrieve Students having subject as english or maths using join query with RequestParam
    @Override
    @Transactional(readOnly = true)
    public List<StudentDTO> findFour(List<String> subjectname) {
        log.debug("Request to get Student : {}", subjectname);

        List<Student> studentlist1= studentRepository.findBySubjectname(subjectname);
        return studentlist1.stream().map(studentMapper::toDto).collect(Collectors.toList());
    }
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
    }
}
