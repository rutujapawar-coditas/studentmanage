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
 * Criteria class for the {@link com.rutu.studentmanage.domain.Subject} entity. This class is used
 * in {@link com.rutu.studentmanage.web.rest.SubjectResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /subjects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SubjectCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter subjectname;

    private StringFilter bookname;

    private IntegerFilter dailyhours;

    private LongFilter studentId;

    public SubjectCriteria() {}

    public SubjectCriteria(SubjectCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.subjectname = other.subjectname == null ? null : other.subjectname.copy();
        this.bookname = other.bookname == null ? null : other.bookname.copy();
        this.dailyhours = other.dailyhours == null ? null : other.dailyhours.copy();
        this.studentId = other.studentId == null ? null : other.studentId.copy();
    }

    @Override
    public SubjectCriteria copy() {
        return new SubjectCriteria(this);
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

    public StringFilter getSubjectname() {
        return subjectname;
    }

    public StringFilter subjectname() {
        if (subjectname == null) {
            subjectname = new StringFilter();
        }
        return subjectname;
    }

    public void setSubjectname(StringFilter subjectname) {
        this.subjectname = subjectname;
    }

    public StringFilter getBookname() {
        return bookname;
    }

    public StringFilter bookname() {
        if (bookname == null) {
            bookname = new StringFilter();
        }
        return bookname;
    }

    public void setBookname(StringFilter bookname) {
        this.bookname = bookname;
    }

    public IntegerFilter getDailyhours() {
        return dailyhours;
    }

    public IntegerFilter dailyhours() {
        if (dailyhours == null) {
            dailyhours = new IntegerFilter();
        }
        return dailyhours;
    }

    public void setDailyhours(IntegerFilter dailyhours) {
        this.dailyhours = dailyhours;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public LongFilter studentId() {
        if (studentId == null) {
            studentId = new LongFilter();
        }
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SubjectCriteria that = (SubjectCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(subjectname, that.subjectname) &&
            Objects.equals(bookname, that.bookname) &&
            Objects.equals(dailyhours, that.dailyhours) &&
            Objects.equals(studentId, that.studentId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subjectname, bookname, dailyhours, studentId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubjectCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (subjectname != null ? "subjectname=" + subjectname + ", " : "") +
            (bookname != null ? "bookname=" + bookname + ", " : "") +
            (dailyhours != null ? "dailyhours=" + dailyhours + ", " : "") +
            (studentId != null ? "studentId=" + studentId + ", " : "") +
            "}";
    }
}
