package com.rutu.studentmanage.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.rutu.studentmanage.domain.Student} entity. This class is used
 * in {@link com.rutu.studentmanage.web.rest.StudentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /students?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter studentname;

    private IntegerFilter standard;

    private IntegerFilter rollno;

    private IntegerFilter age;

    private LongFilter subjectId;

    public StudentCriteria() {}

    public StudentCriteria(StudentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.studentname = other.studentname == null ? null : other.studentname.copy();
        this.standard = other.standard == null ? null : other.standard.copy();
        this.rollno = other.rollno == null ? null : other.rollno.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.subjectId = other.subjectId == null ? null : other.subjectId.copy();
    }

    @Override
    public StudentCriteria copy() {
        return new StudentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStudentname() {
        return studentname;
    }

    public StringFilter studentname() {
        if (studentname == null) {
            studentname = new StringFilter();
        }
        return studentname;
    }

    public void setStudentname(StringFilter studentname) {
        this.studentname = studentname;
    }

    public IntegerFilter getStandard() {
        return standard;
    }

    public IntegerFilter standard() {
        if (standard == null) {
            standard = new IntegerFilter();
        }
        return standard;
    }

    public void setStandard(IntegerFilter standard) {
        this.standard = standard;
    }

    public IntegerFilter getRollno() {
        return rollno;
    }

    public IntegerFilter rollno() {
        if (rollno == null) {
            rollno = new IntegerFilter();
        }
        return rollno;
    }

    public void setRollno(IntegerFilter rollno) {
        this.rollno = rollno;
    }

    public IntegerFilter getAge() {
        return age;
    }

    public IntegerFilter age() {
        if (age == null) {
            age = new IntegerFilter();
        }
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
    }

    public LongFilter getSubjectId() {
        return subjectId;
    }

    public LongFilter subjectId() {
        if (subjectId == null) {
            subjectId = new LongFilter();
        }
        return subjectId;
    }

    public void setSubjectId(LongFilter subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StudentCriteria that = (StudentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(studentname, that.studentname) &&
            Objects.equals(standard, that.standard) &&
            Objects.equals(rollno, that.rollno) &&
            Objects.equals(age, that.age) &&
            Objects.equals(subjectId, that.subjectId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentname, standard, rollno, age, subjectId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (studentname != null ? "studentname=" + studentname + ", " : "") +
            (standard != null ? "standard=" + standard + ", " : "") +
            (rollno != null ? "rollno=" + rollno + ", " : "") +
            (age != null ? "age=" + age + ", " : "") +
            (subjectId != null ? "subjectId=" + subjectId + ", " : "") +
            "}";
    }
}
