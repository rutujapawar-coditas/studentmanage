package com.rutu.studentmanage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rutu.studentmanage.IntegrationTest;
import com.rutu.studentmanage.domain.Student;
import com.rutu.studentmanage.domain.Subject;
import com.rutu.studentmanage.repository.SubjectRepository;
import com.rutu.studentmanage.service.criteria.SubjectCriteria;
import com.rutu.studentmanage.service.dto.SubjectDTO;
import com.rutu.studentmanage.service.mapper.SubjectMapper;
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
 * Integration tests for the {@link SubjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubjectResourceIT {

    private static final String DEFAULT_SUBJECTNAME = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_BOOKNAME = "AAAAAAAAAA";
    private static final String UPDATED_BOOKNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DAILYHOURS = 1;
    private static final Integer UPDATED_DAILYHOURS = 2;
    private static final Integer SMALLER_DAILYHOURS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/subjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubjectMockMvc;

    private Subject subject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subject createEntity(EntityManager em) {
        Subject subject = new Subject().subjectname(DEFAULT_SUBJECTNAME).bookname(DEFAULT_BOOKNAME).dailyhours(DEFAULT_DAILYHOURS);
        return subject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subject createUpdatedEntity(EntityManager em) {
        Subject subject = new Subject().subjectname(UPDATED_SUBJECTNAME).bookname(UPDATED_BOOKNAME).dailyhours(UPDATED_DAILYHOURS);
        return subject;
    }

    @BeforeEach
    public void initTest() {
        subject = createEntity(em);
    }

    @Test
    @Transactional
    void createSubject() throws Exception {
        int databaseSizeBeforeCreate = subjectRepository.findAll().size();
        // Create the Subject
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);
        restSubjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isCreated());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeCreate + 1);
        Subject testSubject = subjectList.get(subjectList.size() - 1);
        assertThat(testSubject.getSubjectname()).isEqualTo(DEFAULT_SUBJECTNAME);
        assertThat(testSubject.getBookname()).isEqualTo(DEFAULT_BOOKNAME);
        assertThat(testSubject.getDailyhours()).isEqualTo(DEFAULT_DAILYHOURS);
    }

    @Test
    @Transactional
    void createSubjectWithExistingId() throws Exception {
        // Create the Subject with an existing ID
        subject.setId(1L);
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        int databaseSizeBeforeCreate = subjectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSubjectnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subjectRepository.findAll().size();
        // set the field null
        subject.setSubjectname(null);

        // Create the Subject, which fails.
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        restSubjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isBadRequest());

        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBooknameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subjectRepository.findAll().size();
        // set the field null
        subject.setBookname(null);

        // Create the Subject, which fails.
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        restSubjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isBadRequest());

        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDailyhoursIsRequired() throws Exception {
        int databaseSizeBeforeTest = subjectRepository.findAll().size();
        // set the field null
        subject.setDailyhours(null);

        // Create the Subject, which fails.
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        restSubjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isBadRequest());

        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSubjects() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList
        restSubjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subject.getId().intValue())))
            .andExpect(jsonPath("$.[*].subjectname").value(hasItem(DEFAULT_SUBJECTNAME)))
            .andExpect(jsonPath("$.[*].bookname").value(hasItem(DEFAULT_BOOKNAME)))
            .andExpect(jsonPath("$.[*].dailyhours").value(hasItem(DEFAULT_DAILYHOURS)));
    }

    @Test
    @Transactional
    void getSubject() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get the subject
        restSubjectMockMvc
            .perform(get(ENTITY_API_URL_ID, subject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subject.getId().intValue()))
            .andExpect(jsonPath("$.subjectname").value(DEFAULT_SUBJECTNAME))
            .andExpect(jsonPath("$.bookname").value(DEFAULT_BOOKNAME))
            .andExpect(jsonPath("$.dailyhours").value(DEFAULT_DAILYHOURS));
    }

    @Test
    @Transactional
    void getSubjectsByIdFiltering() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        Long id = subject.getId();

        defaultSubjectShouldBeFound("id.equals=" + id);
        defaultSubjectShouldNotBeFound("id.notEquals=" + id);

        defaultSubjectShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubjectShouldNotBeFound("id.greaterThan=" + id);

        defaultSubjectShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubjectShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSubjectsBySubjectnameIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectname equals to DEFAULT_SUBJECTNAME
        defaultSubjectShouldBeFound("subjectname.equals=" + DEFAULT_SUBJECTNAME);

        // Get all the subjectList where subjectname equals to UPDATED_SUBJECTNAME
        defaultSubjectShouldNotBeFound("subjectname.equals=" + UPDATED_SUBJECTNAME);
    }

    @Test
    @Transactional
    void getAllSubjectsBySubjectnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectname not equals to DEFAULT_SUBJECTNAME
        defaultSubjectShouldNotBeFound("subjectname.notEquals=" + DEFAULT_SUBJECTNAME);

        // Get all the subjectList where subjectname not equals to UPDATED_SUBJECTNAME
        defaultSubjectShouldBeFound("subjectname.notEquals=" + UPDATED_SUBJECTNAME);
    }

    @Test
    @Transactional
    void getAllSubjectsBySubjectnameIsInShouldWork() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectname in DEFAULT_SUBJECTNAME or UPDATED_SUBJECTNAME
        defaultSubjectShouldBeFound("subjectname.in=" + DEFAULT_SUBJECTNAME + "," + UPDATED_SUBJECTNAME);

        // Get all the subjectList where subjectname equals to UPDATED_SUBJECTNAME
        defaultSubjectShouldNotBeFound("subjectname.in=" + UPDATED_SUBJECTNAME);
    }

    @Test
    @Transactional
    void getAllSubjectsBySubjectnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectname is not null
        defaultSubjectShouldBeFound("subjectname.specified=true");

        // Get all the subjectList where subjectname is null
        defaultSubjectShouldNotBeFound("subjectname.specified=false");
    }

    @Test
    @Transactional
    void getAllSubjectsBySubjectnameContainsSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectname contains DEFAULT_SUBJECTNAME
        defaultSubjectShouldBeFound("subjectname.contains=" + DEFAULT_SUBJECTNAME);

        // Get all the subjectList where subjectname contains UPDATED_SUBJECTNAME
        defaultSubjectShouldNotBeFound("subjectname.contains=" + UPDATED_SUBJECTNAME);
    }

    @Test
    @Transactional
    void getAllSubjectsBySubjectnameNotContainsSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectname does not contain DEFAULT_SUBJECTNAME
        defaultSubjectShouldNotBeFound("subjectname.doesNotContain=" + DEFAULT_SUBJECTNAME);

        // Get all the subjectList where subjectname does not contain UPDATED_SUBJECTNAME
        defaultSubjectShouldBeFound("subjectname.doesNotContain=" + UPDATED_SUBJECTNAME);
    }

    @Test
    @Transactional
    void getAllSubjectsByBooknameIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where bookname equals to DEFAULT_BOOKNAME
        defaultSubjectShouldBeFound("bookname.equals=" + DEFAULT_BOOKNAME);

        // Get all the subjectList where bookname equals to UPDATED_BOOKNAME
        defaultSubjectShouldNotBeFound("bookname.equals=" + UPDATED_BOOKNAME);
    }

    @Test
    @Transactional
    void getAllSubjectsByBooknameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where bookname not equals to DEFAULT_BOOKNAME
        defaultSubjectShouldNotBeFound("bookname.notEquals=" + DEFAULT_BOOKNAME);

        // Get all the subjectList where bookname not equals to UPDATED_BOOKNAME
        defaultSubjectShouldBeFound("bookname.notEquals=" + UPDATED_BOOKNAME);
    }

    @Test
    @Transactional
    void getAllSubjectsByBooknameIsInShouldWork() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where bookname in DEFAULT_BOOKNAME or UPDATED_BOOKNAME
        defaultSubjectShouldBeFound("bookname.in=" + DEFAULT_BOOKNAME + "," + UPDATED_BOOKNAME);

        // Get all the subjectList where bookname equals to UPDATED_BOOKNAME
        defaultSubjectShouldNotBeFound("bookname.in=" + UPDATED_BOOKNAME);
    }

    @Test
    @Transactional
    void getAllSubjectsByBooknameIsNullOrNotNull() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where bookname is not null
        defaultSubjectShouldBeFound("bookname.specified=true");

        // Get all the subjectList where bookname is null
        defaultSubjectShouldNotBeFound("bookname.specified=false");
    }

    @Test
    @Transactional
    void getAllSubjectsByBooknameContainsSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where bookname contains DEFAULT_BOOKNAME
        defaultSubjectShouldBeFound("bookname.contains=" + DEFAULT_BOOKNAME);

        // Get all the subjectList where bookname contains UPDATED_BOOKNAME
        defaultSubjectShouldNotBeFound("bookname.contains=" + UPDATED_BOOKNAME);
    }

    @Test
    @Transactional
    void getAllSubjectsByBooknameNotContainsSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where bookname does not contain DEFAULT_BOOKNAME
        defaultSubjectShouldNotBeFound("bookname.doesNotContain=" + DEFAULT_BOOKNAME);

        // Get all the subjectList where bookname does not contain UPDATED_BOOKNAME
        defaultSubjectShouldBeFound("bookname.doesNotContain=" + UPDATED_BOOKNAME);
    }

    @Test
    @Transactional
    void getAllSubjectsByDailyhoursIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where dailyhours equals to DEFAULT_DAILYHOURS
        defaultSubjectShouldBeFound("dailyhours.equals=" + DEFAULT_DAILYHOURS);

        // Get all the subjectList where dailyhours equals to UPDATED_DAILYHOURS
        defaultSubjectShouldNotBeFound("dailyhours.equals=" + UPDATED_DAILYHOURS);
    }

    @Test
    @Transactional
    void getAllSubjectsByDailyhoursIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where dailyhours not equals to DEFAULT_DAILYHOURS
        defaultSubjectShouldNotBeFound("dailyhours.notEquals=" + DEFAULT_DAILYHOURS);

        // Get all the subjectList where dailyhours not equals to UPDATED_DAILYHOURS
        defaultSubjectShouldBeFound("dailyhours.notEquals=" + UPDATED_DAILYHOURS);
    }

    @Test
    @Transactional
    void getAllSubjectsByDailyhoursIsInShouldWork() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where dailyhours in DEFAULT_DAILYHOURS or UPDATED_DAILYHOURS
        defaultSubjectShouldBeFound("dailyhours.in=" + DEFAULT_DAILYHOURS + "," + UPDATED_DAILYHOURS);

        // Get all the subjectList where dailyhours equals to UPDATED_DAILYHOURS
        defaultSubjectShouldNotBeFound("dailyhours.in=" + UPDATED_DAILYHOURS);
    }

    @Test
    @Transactional
    void getAllSubjectsByDailyhoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where dailyhours is not null
        defaultSubjectShouldBeFound("dailyhours.specified=true");

        // Get all the subjectList where dailyhours is null
        defaultSubjectShouldNotBeFound("dailyhours.specified=false");
    }

    @Test
    @Transactional
    void getAllSubjectsByDailyhoursIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where dailyhours is greater than or equal to DEFAULT_DAILYHOURS
        defaultSubjectShouldBeFound("dailyhours.greaterThanOrEqual=" + DEFAULT_DAILYHOURS);

        // Get all the subjectList where dailyhours is greater than or equal to UPDATED_DAILYHOURS
        defaultSubjectShouldNotBeFound("dailyhours.greaterThanOrEqual=" + UPDATED_DAILYHOURS);
    }

    @Test
    @Transactional
    void getAllSubjectsByDailyhoursIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where dailyhours is less than or equal to DEFAULT_DAILYHOURS
        defaultSubjectShouldBeFound("dailyhours.lessThanOrEqual=" + DEFAULT_DAILYHOURS);

        // Get all the subjectList where dailyhours is less than or equal to SMALLER_DAILYHOURS
        defaultSubjectShouldNotBeFound("dailyhours.lessThanOrEqual=" + SMALLER_DAILYHOURS);
    }

    @Test
    @Transactional
    void getAllSubjectsByDailyhoursIsLessThanSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where dailyhours is less than DEFAULT_DAILYHOURS
        defaultSubjectShouldNotBeFound("dailyhours.lessThan=" + DEFAULT_DAILYHOURS);

        // Get all the subjectList where dailyhours is less than UPDATED_DAILYHOURS
        defaultSubjectShouldBeFound("dailyhours.lessThan=" + UPDATED_DAILYHOURS);
    }

    @Test
    @Transactional
    void getAllSubjectsByDailyhoursIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where dailyhours is greater than DEFAULT_DAILYHOURS
        defaultSubjectShouldNotBeFound("dailyhours.greaterThan=" + DEFAULT_DAILYHOURS);

        // Get all the subjectList where dailyhours is greater than SMALLER_DAILYHOURS
        defaultSubjectShouldBeFound("dailyhours.greaterThan=" + SMALLER_DAILYHOURS);
    }

    @Test
    @Transactional
    void getAllSubjectsByStudentIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);
        Student student = StudentResourceIT.createEntity(em);
        em.persist(student);
        em.flush();
        subject.setStudent(student);
        subjectRepository.saveAndFlush(subject);
        Long studentId = student.getId();

        // Get all the subjectList where student equals to studentId
        defaultSubjectShouldBeFound("studentId.equals=" + studentId);

        // Get all the subjectList where student equals to (studentId + 1)
        defaultSubjectShouldNotBeFound("studentId.equals=" + (studentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubjectShouldBeFound(String filter) throws Exception {
        restSubjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subject.getId().intValue())))
            .andExpect(jsonPath("$.[*].subjectname").value(hasItem(DEFAULT_SUBJECTNAME)))
            .andExpect(jsonPath("$.[*].bookname").value(hasItem(DEFAULT_BOOKNAME)))
            .andExpect(jsonPath("$.[*].dailyhours").value(hasItem(DEFAULT_DAILYHOURS)));

        // Check, that the count call also returns 1
        restSubjectMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubjectShouldNotBeFound(String filter) throws Exception {
        restSubjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubjectMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSubject() throws Exception {
        // Get the subject
        restSubjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubject() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();

        // Update the subject
        Subject updatedSubject = subjectRepository.findById(subject.getId()).get();
        // Disconnect from session so that the updates on updatedSubject are not directly saved in db
        em.detach(updatedSubject);
        updatedSubject.subjectname(UPDATED_SUBJECTNAME).bookname(UPDATED_BOOKNAME).dailyhours(UPDATED_DAILYHOURS);
        SubjectDTO subjectDTO = subjectMapper.toDto(updatedSubject);

        restSubjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subjectDTO))
            )
            .andExpect(status().isOk());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);
        Subject testSubject = subjectList.get(subjectList.size() - 1);
        assertThat(testSubject.getSubjectname()).isEqualTo(UPDATED_SUBJECTNAME);
        assertThat(testSubject.getBookname()).isEqualTo(UPDATED_BOOKNAME);
        assertThat(testSubject.getDailyhours()).isEqualTo(UPDATED_DAILYHOURS);
    }

    @Test
    @Transactional
    void putNonExistingSubject() throws Exception {
        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();
        subject.setId(count.incrementAndGet());

        // Create the Subject
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubject() throws Exception {
        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();
        subject.setId(count.incrementAndGet());

        // Create the Subject
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubject() throws Exception {
        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();
        subject.setId(count.incrementAndGet());

        // Create the Subject
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubjectWithPatch() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();

        // Update the subject using partial update
        Subject partialUpdatedSubject = new Subject();
        partialUpdatedSubject.setId(subject.getId());

        partialUpdatedSubject.subjectname(UPDATED_SUBJECTNAME);

        restSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubject))
            )
            .andExpect(status().isOk());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);
        Subject testSubject = subjectList.get(subjectList.size() - 1);
        assertThat(testSubject.getSubjectname()).isEqualTo(UPDATED_SUBJECTNAME);
        assertThat(testSubject.getBookname()).isEqualTo(DEFAULT_BOOKNAME);
        assertThat(testSubject.getDailyhours()).isEqualTo(DEFAULT_DAILYHOURS);
    }

    @Test
    @Transactional
    void fullUpdateSubjectWithPatch() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();

        // Update the subject using partial update
        Subject partialUpdatedSubject = new Subject();
        partialUpdatedSubject.setId(subject.getId());

        partialUpdatedSubject.subjectname(UPDATED_SUBJECTNAME).bookname(UPDATED_BOOKNAME).dailyhours(UPDATED_DAILYHOURS);

        restSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubject))
            )
            .andExpect(status().isOk());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);
        Subject testSubject = subjectList.get(subjectList.size() - 1);
        assertThat(testSubject.getSubjectname()).isEqualTo(UPDATED_SUBJECTNAME);
        assertThat(testSubject.getBookname()).isEqualTo(UPDATED_BOOKNAME);
        assertThat(testSubject.getDailyhours()).isEqualTo(UPDATED_DAILYHOURS);
    }

    @Test
    @Transactional
    void patchNonExistingSubject() throws Exception {
        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();
        subject.setId(count.incrementAndGet());

        // Create the Subject
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subjectDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubject() throws Exception {
        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();
        subject.setId(count.incrementAndGet());

        // Create the Subject
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubject() throws Exception {
        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();
        subject.setId(count.incrementAndGet());

        // Create the Subject
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subjectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubject() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        int databaseSizeBeforeDelete = subjectRepository.findAll().size();

        // Delete the subject
        restSubjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, subject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
