package com.rutu.studentmanage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "studentname", length = 20, nullable = false)
    private String studentname;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    @Column(name = "standard", nullable = false)
    private Integer standard;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    @Column(name = "rollno", nullable = false)
    private Integer rollno;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    @Column(name = "age", nullable = false)
    private Integer age;

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties(value = { "student" }, allowSetters = true)
    private Set<Subject> subjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student id(Long id) {
        this.id = id;
        return this;
    }

    public String getStudentname() {
        return this.studentname;
    }

    public Student studentname(String studentname) {
        this.studentname = studentname;
        return this;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public Integer getStandard() {
        return this.standard;
    }

    public Student standard(Integer standard) {
        this.standard = standard;
        return this;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    public Integer getRollno() {
        return this.rollno;
    }

    public Student rollno(Integer rollno) {
        this.rollno = rollno;
        return this;
    }

    public void setRollno(Integer rollno) {
        this.rollno = rollno;
    }

    public Integer getAge() {
        return this.age;
    }

    public Student age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Subject> getSubjects() {
        return this.subjects;
    }

    public Student subjects(Set<Subject> subjects) {
        this.setSubjects(subjects);
        return this;
    }

    public Student addSubject(Subject subject) {
        this.subjects.add(subject);
        subject.setStudent(this);
        return this;
    }

    public Student removeSubject(Subject subject) {
        this.subjects.remove(subject);
        subject.setStudent(null);
        return this;
    }

    public void setSubjects(Set<Subject> subjects) {
        if (this.subjects != null) {
            this.subjects.forEach(i -> i.setStudent(null));
        }
        if (subjects != null) {
            subjects.forEach(i -> i.setStudent(this));
        }
        this.subjects = subjects;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", studentname='" + getStudentname() + "'" +
            ", standard=" + getStandard() +
            ", rollno=" + getRollno() +
            ", age=" + getAge() +
            "}";
    }
}
