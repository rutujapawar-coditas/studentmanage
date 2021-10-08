package com.rutu.studentmanage.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.rutu.studentmanage.domain.Subject} entity.
 */
public class SubjectDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String subjectname;

    @NotNull
    @Size(min = 3, max = 20)
    private String bookname;

    @NotNull
    @Min(value = 1)
    private Integer dailyhours;

    private StudentDTO student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Integer getDailyhours() {
        return dailyhours;
    }

    public void setDailyhours(Integer dailyhours) {
        this.dailyhours = dailyhours;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubjectDTO)) {
            return false;
        }

        SubjectDTO subjectDTO = (SubjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subjectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubjectDTO{" +
            "id=" + getId() +
            ", subjectname='" + getSubjectname() + "'" +
            ", bookname='" + getBookname() + "'" +
            ", dailyhours=" + getDailyhours() +
            ", student=" + getStudent() +
            "}";
    }
}
