package com.rutu.studentmanage.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.rutu.studentmanage.domain.Student} entity.
 */
public class StudentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String studentname;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    private Integer standard;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    private Integer rollno;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public Integer getStandard() {
        return standard;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    public Integer getRollno() {
        return rollno;
    }

    public void setRollno(Integer rollno) {
        this.rollno = rollno;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentDTO)) {
            return false;
        }

        StudentDTO studentDTO = (StudentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, studentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentDTO{" +
            "id=" + getId() +
            ", studentname='" + getStudentname() + "'" +
            ", standard=" + getStandard() +
            ", rollno=" + getRollno() +
            ", age=" + getAge() +
            "}";
    }
}
