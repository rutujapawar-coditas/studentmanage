package com.rutu.studentmanage.service.impl;

import com.rutu.studentmanage.domain.Subject;
import com.rutu.studentmanage.repository.SubjectRepository;
import com.rutu.studentmanage.service.SubjectService;
import com.rutu.studentmanage.service.dto.SubjectDTO;
import com.rutu.studentmanage.service.mapper.SubjectMapper;
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
 * Service Implementation for managing {@link Subject}.
 */
@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final Logger log = LoggerFactory.getLogger(SubjectServiceImpl.class);

    private final SubjectRepository subjectRepository;

    private final SubjectMapper subjectMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public SubjectDTO save(SubjectDTO subjectDTO) {
        log.debug("Request to save Subject : {}", subjectDTO);
        Subject subject = subjectMapper.toEntity(subjectDTO);
        subject = subjectRepository.save(subject);
        return subjectMapper.toDto(subject);
    }

    @Override
    public Optional<SubjectDTO> partialUpdate(SubjectDTO subjectDTO) {
        log.debug("Request to partially update Subject : {}", subjectDTO);

        return subjectRepository
            .findById(subjectDTO.getId())
            .map(
                existingSubject -> {
                    subjectMapper.partialUpdate(existingSubject, subjectDTO);

                    return existingSubject;
                }
            )
            .map(subjectRepository::save)
            .map(subjectMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Subjects");
        return subjectRepository.findAll(pageable).map(subjectMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubjectDTO> findOne(Long id) {
        log.debug("Request to get Subject : {}", id);
        return subjectRepository.findById(id).map(subjectMapper::toDto);
    }

    // Retrieve student by subjectname
    @Override
    @Transactional(readOnly = true)
    public List<SubjectDTO> findTwo(String subjectname) {
        log.debug("Request to get Subject : {}", subjectname);
        List <Subject> sublist =subjectRepository.findBySubjectname(subjectname);
        return sublist.stream().map(subjectMapper::toDto).collect(Collectors.toList());
    }

    // Retrieve student having subjectname IN (English,maths,Sci)
    @Override
    @Transactional(readOnly = true)
    public List<SubjectDTO> findThree(String subjectname) {
        log.debug("Request to get Subject : {}", subjectname);
        List<String> subject=new ArrayList<>();
        subject.add("English");
        subject.add("Maths");
        subject.add("Science");
        List <Subject> subjectlist =subjectRepository.findBySubjectnameIn(subject);
        return subjectlist.stream().map(subjectMapper::toDto).collect(Collectors.toList());
    }

    // Retrieve student having subjectname or bookname as "maths"
    @Override
    @Transactional(readOnly = true)
    public List<SubjectDTO> findFour(String subjectvalue, String bookvalue) {
        log.debug("Request to get Subject : {}", subjectvalue,bookvalue);
        List <Subject> subjectlist1 =subjectRepository.findBySubjectnameOrBookname(subjectvalue, bookvalue);
        return subjectlist1.stream().map(subjectMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public void delete(Long id) {
        log.debug("Request to delete Subject : {}", id);
        subjectRepository.deleteById(id);
    }
}
