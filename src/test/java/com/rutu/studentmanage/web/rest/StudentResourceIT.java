package com.rutu.studentmanage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rutu.studentmanage.IntegrationTest;
import com.rutu.studentmanage.domain.Student;
import com.rutu.studentmanage.domain.Subject;
import com.rutu.studentmanage.repository.StudentRepository;
import com.rutu.studentmanage.service.criteria.StudentCriteria;
import com.rutu.studentmanage.service.dto.StudentDTO;
import com.rutu.studentmanage.service.mapper.StudentMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StudentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudentResourceIT {

    private static final String DEFAULT_STUDENTNAME = "AAAAAAAAAA";
    private static final String UPDATED_STUDENTNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STANDARD = 1;
    private static final Integer UPDATED_STANDARD = 2;
    private static final Integer SMALLER_STANDARD = 1 - 1;

    private static final Integer DEFAULT_ROLLNO = 1;
    private static final Integer UPDATED_ROLLNO = 2;
    private static final Integer SMALLER_ROLLNO = 1 - 1;

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final Integer SMALLER_AGE = 1 - 1;

    private static final String ENTITY_API_URL = "/api/students";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentMockMvc;

    private Student student;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createEntity(EntityManager em) {
        Student student = new Student().studentname(DEFAULT_STUDENTNAME).standard(DEFAULT_STANDARD).rollno(DEFAULT_ROLLNO).age(DEFAULT_AGE);
        return student;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createUpdatedEntity(EntityManager em) {
        Student student = new Student().studentname(UPDATED_STUDENTNAME).standard(UPDATED_STANDARD).rollno(UPDATED_ROLLNO).age(UPDATED_AGE);
        return student;
    }

    @BeforeEach
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();
        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);
        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentname()).isEqualTo(DEFAULT_STUDENTNAME);
        assertThat(testStudent.getStandard()).isEqualTo(DEFAULT_STANDARD);
        assertThat(testStudent.getRollno()).isEqualTo(DEFAULT_ROLLNO);
        assertThat(testStudent.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @Test
    @Transactional
    void createStudentWithExistingId() throws Exception {
        // Create the Student with an existing ID
        student.setId(1L);
        StudentDTO studentDTO = studentMapper.toDto(student);

        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStudentnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setStudentname(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStandardIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setStandard(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRollnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setRollno(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setAge(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentname").value(hasItem(DEFAULT_STUDENTNAME)))
            .andExpect(jsonPath("$.[*].standard").value(hasItem(DEFAULT_STANDARD)))
            .andExpect(jsonPath("$.[*].rollno").value(hasItem(DEFAULT_ROLLNO)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)));
    }

    @Test
    @Transactional
    void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc
            .perform(get(ENTITY_API_URL_ID, student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.studentname").value(DEFAULT_STUDENTNAME))
            .andExpect(jsonPath("$.standard").value(DEFAULT_STANDARD))
            .andExpect(jsonPath("$.rollno").value(DEFAULT_ROLLNO))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE));
    }

    @Test
    @Transactional
    void getStudentsByIdFiltering() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        Long id = student.getId();

        defaultStudentShouldBeFound("id.equals=" + id);
        defaultStudentShouldNotBeFound("id.notEquals=" + id);

        defaultStudentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStudentShouldNotBeFound("id.greaterThan=" + id);

        defaultStudentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStudentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStudentsByStudentnameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentname equals to DEFAULT_STUDENTNAME
        defaultStudentShouldBeFound("studentname.equals=" + DEFAULT_STUDENTNAME);

        // Get all the studentList where studentname equals to UPDATED_STUDENTNAME
        defaultStudentShouldNotBeFound("studentname.equals=" + UPDATED_STUDENTNAME);
    }

    @Test
    @Transactional
    void getAllStudentsByStudentnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentname not equals to DEFAULT_STUDENTNAME
        defaultStudentShouldNotBeFound("studentname.notEquals=" + DEFAULT_STUDENTNAME);

        // Get all the studentList where studentname not equals to UPDATED_STUDENTNAME
        defaultStudentShouldBeFound("studentname.notEquals=" + UPDATED_STUDENTNAME);
    }

    @Test
    @Transactional
    void getAllStudentsByStudentnameIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentname in DEFAULT_STUDENTNAME or UPDATED_STUDENTNAME
        defaultStudentShouldBeFound("studentname.in=" + DEFAULT_STUDENTNAME + "," + UPDATED_STUDENTNAME);

        // Get all the studentList where studentname equals to UPDATED_STUDENTNAME
        defaultStudentShouldNotBeFound("studentname.in=" + UPDATED_STUDENTNAME);
    }

    @Test
    @Transactional
    void getAllStudentsByStudentnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentname is not null
        defaultStudentShouldBeFound("studentname.specified=true");

        // Get all the studentList where studentname is null
        defaultStudentShouldNotBeFound("studentname.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByStudentnameContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentname contains DEFAULT_STUDENTNAME
        defaultStudentShouldBeFound("studentname.contains=" + DEFAULT_STUDENTNAME);

        // Get all the studentList where studentname contains UPDATED_STUDENTNAME
        defaultStudentShouldNotBeFound("studentname.contains=" + UPDATED_STUDENTNAME);
    }

    @Test
    @Transactional
    void getAllStudentsByStudentnameNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentname does not contain DEFAULT_STUDENTNAME
        defaultStudentShouldNotBeFound("studentname.doesNotContain=" + DEFAULT_STUDENTNAME);

        // Get all the studentList where studentname does not contain UPDATED_STUDENTNAME
        defaultStudentShouldBeFound("studentname.doesNotContain=" + UPDATED_STUDENTNAME);
    }

    @Test
    @Transactional
    void getAllStudentsByStandardIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where standard equals to DEFAULT_STANDARD
        defaultStudentShouldBeFound("standard.equals=" + DEFAULT_STANDARD);

        // Get all the studentList where standard equals to UPDATED_STANDARD
        defaultStudentShouldNotBeFound("standard.equals=" + UPDATED_STANDARD);
    }

    @Test
    @Transactional
    void getAllStudentsByStandardIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where standard not equals to DEFAULT_STANDARD
        defaultStudentShouldNotBeFound("standard.notEquals=" + DEFAULT_STANDARD);

        // Get all the studentList where standard not equals to UPDATED_STANDARD
        defaultStudentShouldBeFound("standard.notEquals=" + UPDATED_STANDARD);
    }

    @Test
    @Transactional
    void getAllStudentsByStandardIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where standard in DEFAULT_STANDARD or UPDATED_STANDARD
        defaultStudentShouldBeFound("standard.in=" + DEFAULT_STANDARD + "," + UPDATED_STANDARD);

        // Get all the studentList where standard equals to UPDATED_STANDARD
        defaultStudentShouldNotBeFound("standard.in=" + UPDATED_STANDARD);
    }

    @Test
    @Transactional
    void getAllStudentsByStandardIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where standard is not null
        defaultStudentShouldBeFound("standard.specified=true");

        // Get all the studentList where standard is null
        defaultStudentShouldNotBeFound("standard.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByStandardIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where standard is greater than or equal to DEFAULT_STANDARD
        defaultStudentShouldBeFound("standard.greaterThanOrEqual=" + DEFAULT_STANDARD);

        // Get all the studentList where standard is greater than or equal to (DEFAULT_STANDARD + 1)
        defaultStudentShouldNotBeFound("standard.greaterThanOrEqual=" + (DEFAULT_STANDARD + 1));
    }

    @Test
    @Transactional
    void getAllStudentsByStandardIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where standard is less than or equal to DEFAULT_STANDARD
        defaultStudentShouldBeFound("standard.lessThanOrEqual=" + DEFAULT_STANDARD);

        // Get all the studentList where standard is less than or equal to SMALLER_STANDARD
        defaultStudentShouldNotBeFound("standard.lessThanOrEqual=" + SMALLER_STANDARD);
    }

    @Test
    @Transactional
    void getAllStudentsByStandardIsLessThanSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where standard is less than DEFAULT_STANDARD
        defaultStudentShouldNotBeFound("standard.lessThan=" + DEFAULT_STANDARD);

        // Get all the studentList where standard is less than (DEFAULT_STANDARD + 1)
        defaultStudentShouldBeFound("standard.lessThan=" + (DEFAULT_STANDARD + 1));
    }

    @Test
    @Transactional
    void getAllStudentsByStandardIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where standard is greater than DEFAULT_STANDARD
        defaultStudentShouldNotBeFound("standard.greaterThan=" + DEFAULT_STANDARD);

        // Get all the studentList where standard is greater than SMALLER_STANDARD
        defaultStudentShouldBeFound("standard.greaterThan=" + SMALLER_STANDARD);
    }

    @Test
    @Transactional
    void getAllStudentsByRollnoIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where rollno equals to DEFAULT_ROLLNO
        defaultStudentShouldBeFound("rollno.equals=" + DEFAULT_ROLLNO);

        // Get all the studentList where rollno equals to UPDATED_ROLLNO
        defaultStudentShouldNotBeFound("rollno.equals=" + UPDATED_ROLLNO);
    }

    @Test
    @Transactional
    void getAllStudentsByRollnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where rollno not equals to DEFAULT_ROLLNO
        defaultStudentShouldNotBeFound("rollno.notEquals=" + DEFAULT_ROLLNO);

        // Get all the studentList where rollno not equals to UPDATED_ROLLNO
        defaultStudentShouldBeFound("rollno.notEquals=" + UPDATED_ROLLNO);
    }

    @Test
    @Transactional
    void getAllStudentsByRollnoIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where rollno in DEFAULT_ROLLNO or UPDATED_ROLLNO
        defaultStudentShouldBeFound("rollno.in=" + DEFAULT_ROLLNO + "," + UPDATED_ROLLNO);

        // Get all the studentList where rollno equals to UPDATED_ROLLNO
        defaultStudentShouldNotBeFound("rollno.in=" + UPDATED_ROLLNO);
    }

    @Test
    @Transactional
    void getAllStudentsByRollnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where rollno is not null
        defaultStudentShouldBeFound("rollno.specified=true");

        // Get all the studentList where rollno is null
        defaultStudentShouldNotBeFound("rollno.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByRollnoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where rollno is greater than or equal to DEFAULT_ROLLNO
        defaultStudentShouldBeFound("rollno.greaterThanOrEqual=" + DEFAULT_ROLLNO);

        // Get all the studentList where rollno is greater than or equal to (DEFAULT_ROLLNO + 1)
        defaultStudentShouldNotBeFound("rollno.greaterThanOrEqual=" + (DEFAULT_ROLLNO + 1));
    }

    @Test
    @Transactional
    void getAllStudentsByRollnoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where rollno is less than or equal to DEFAULT_ROLLNO
        defaultStudentShouldBeFound("rollno.lessThanOrEqual=" + DEFAULT_ROLLNO);

        // Get all the studentList where rollno is less than or equal to SMALLER_ROLLNO
        defaultStudentShouldNotBeFound("rollno.lessThanOrEqual=" + SMALLER_ROLLNO);
    }

    @Test
    @Transactional
    void getAllStudentsByRollnoIsLessThanSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where rollno is less than DEFAULT_ROLLNO
        defaultStudentShouldNotBeFound("rollno.lessThan=" + DEFAULT_ROLLNO);

        // Get all the studentList where rollno is less than (DEFAULT_ROLLNO + 1)
        defaultStudentShouldBeFound("rollno.lessThan=" + (DEFAULT_ROLLNO + 1));
    }

    @Test
    @Transactional
    void getAllStudentsByRollnoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where rollno is greater than DEFAULT_ROLLNO
        defaultStudentShouldNotBeFound("rollno.greaterThan=" + DEFAULT_ROLLNO);

        // Get all the studentList where rollno is greater than SMALLER_ROLLNO
        defaultStudentShouldBeFound("rollno.greaterThan=" + SMALLER_ROLLNO);
    }

    @Test
    @Transactional
    void getAllStudentsByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where age equals to DEFAULT_AGE
        defaultStudentShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the studentList where age equals to UPDATED_AGE
        defaultStudentShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllStudentsByAgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where age not equals to DEFAULT_AGE
        defaultStudentShouldNotBeFound("age.notEquals=" + DEFAULT_AGE);

        // Get all the studentList where age not equals to UPDATED_AGE
        defaultStudentShouldBeFound("age.notEquals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllStudentsByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where age in DEFAULT_AGE or UPDATED_AGE
        defaultStudentShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the studentList where age equals to UPDATED_AGE
        defaultStudentShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllStudentsByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where age is not null
        defaultStudentShouldBeFound("age.specified=true");

        // Get all the studentList where age is null
        defaultStudentShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentsByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where age is greater than or equal to DEFAULT_AGE
        defaultStudentShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the studentList where age is greater than or equal to (DEFAULT_AGE + 1)
        defaultStudentShouldNotBeFound("age.greaterThanOrEqual=" + (DEFAULT_AGE + 1));
    }

    @Test
    @Transactional
    void getAllStudentsByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where age is less than or equal to DEFAULT_AGE
        defaultStudentShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the studentList where age is less than or equal to SMALLER_AGE
        defaultStudentShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllStudentsByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where age is less than DEFAULT_AGE
        defaultStudentShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the studentList where age is less than (DEFAULT_AGE + 1)
        defaultStudentShouldBeFound("age.lessThan=" + (DEFAULT_AGE + 1));
    }

    @Test
    @Transactional
    void getAllStudentsByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where age is greater than DEFAULT_AGE
        defaultStudentShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the studentList where age is greater than SMALLER_AGE
        defaultStudentShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllStudentsBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);
        Subject subject = SubjectResourceIT.createEntity(em);
        em.persist(subject);
        em.flush();
        student.addSubject(subject);
        studentRepository.saveAndFlush(student);
        Long subjectId = subject.getId();

        // Get all the studentList where subject equals to subjectId
        defaultStudentShouldBeFound("subjectId.equals=" + subjectId);

        // Get all the studentList where subject equals to (subjectId + 1)
        defaultStudentShouldNotBeFound("subjectId.equals=" + (subjectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStudentShouldBeFound(String filter) throws Exception {
        restStudentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentname").value(hasItem(DEFAULT_STUDENTNAME)))
            .andExpect(jsonPath("$.[*].standard").value(hasItem(DEFAULT_STANDARD)))
            .andExpect(jsonPath("$.[*].rollno").value(hasItem(DEFAULT_ROLLNO)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)));

        // Check, that the count call also returns 1
        restStudentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStudentShouldNotBeFound(String filter) throws Exception {
        restStudentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStudentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent.studentname(UPDATED_STUDENTNAME).standard(UPDATED_STANDARD).rollno(UPDATED_ROLLNO).age(UPDATED_AGE);
        StudentDTO studentDTO = studentMapper.toDto(updatedStudent);

        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentname()).isEqualTo(UPDATED_STUDENTNAME);
        assertThat(testStudent.getStandard()).isEqualTo(UPDATED_STANDARD);
        assertThat(testStudent.getRollno()).isEqualTo(UPDATED_ROLLNO);
        assertThat(testStudent.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    void putNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudentWithPatch() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student using partial update
        Student partialUpdatedStudent = new Student();
        partialUpdatedStudent.setId(student.getId());

        partialUpdatedStudent.rollno(UPDATED_ROLLNO).age(UPDATED_AGE);

        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudent))
            )
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentname()).isEqualTo(DEFAULT_STUDENTNAME);
        assertThat(testStudent.getStandard()).isEqualTo(DEFAULT_STANDARD);
        assertThat(testStudent.getRollno()).isEqualTo(UPDATED_ROLLNO);
        assertThat(testStudent.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    void fullUpdateStudentWithPatch() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student using partial update
        Student partialUpdatedStudent = new Student();
        partialUpdatedStudent.setId(student.getId());

        partialUpdatedStudent.studentname(UPDATED_STUDENTNAME).standard(UPDATED_STANDARD).rollno(UPDATED_ROLLNO).age(UPDATED_AGE);

        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudent))
            )
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentname()).isEqualTo(UPDATED_STUDENTNAME);
        assertThat(testStudent.getStandard()).isEqualTo(UPDATED_STANDARD);
        assertThat(testStudent.getRollno()).isEqualTo(UPDATED_ROLLNO);
        assertThat(testStudent.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    void patchNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc
            .perform(delete(ENTITY_API_URL_ID, student.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
