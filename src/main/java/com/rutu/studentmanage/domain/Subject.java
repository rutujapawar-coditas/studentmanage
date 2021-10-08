package com.rutu.studentmanage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Subject.
 */
@Entity
@Table(name = "subject")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "subjectname", length = 20, nullable = false)
    private String subjectname;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "bookname", length = 20, nullable = false)
    private String bookname;

    @NotNull
    @Min(value = 1)
    @Column(name = "dailyhours", nullable = false)
    private Integer dailyhours;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subjects" }, allowSetters = true)
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject id(Long id) {
        this.id = id;
        return this;
    }

    public String getSubjectname() {
        return this.subjectname;
    }

    public Subject subjectname(String subjectname) {
        this.subjectname = subjectname;
        return this;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getBookname() {
        return this.bookname;
    }

    public Subject bookname(String bookname) {
        this.bookname = bookname;
        return this;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Integer getDailyhours() {
        return this.dailyhours;
    }

    public Subject dailyhours(Integer dailyhours) {
        this.dailyhours = dailyhours;
        return this;
    }

    public void setDailyhours(Integer dailyhours) {
        this.dailyhours = dailyhours;
    }

    public Student getStudent() {
        return this.student;
    }

    public Subject student(Student student) {
        this.setStudent(student);
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subject)) {
            return false;
        }
        return id != null && id.equals(((Subject) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", subjectname='" + getSubjectname() + "'" +
            ", bookname='" + getBookname() + "'" +
            ", dailyhours=" + getDailyhours() +
            "}";
    }
}
