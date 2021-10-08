jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SubjectService } from '../service/subject.service';
import { ISubject, Subject } from '../subject.model';
import { IStudent } from 'app/entities/student/student.model';
import { StudentService } from 'app/entities/student/service/student.service';

import { SubjectUpdateComponent } from './subject-update.component';

describe('Component Tests', () => {
  describe('Subject Management Update Component', () => {
    let comp: SubjectUpdateComponent;
    let fixture: ComponentFixture<SubjectUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let subjectService: SubjectService;
    let studentService: StudentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SubjectUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SubjectUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubjectUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      subjectService = TestBed.inject(SubjectService);
      studentService = TestBed.inject(StudentService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Student query and add missing value', () => {
        const subject: ISubject = { id: 456 };
        const student: IStudent = { id: 69458 };
        subject.student = student;

        const studentCollection: IStudent[] = [{ id: 28911 }];
        jest.spyOn(studentService, 'query').mockReturnValue(of(new HttpResponse({ body: studentCollection })));
        const additionalStudents = [student];
        const expectedCollection: IStudent[] = [...additionalStudents, ...studentCollection];
        jest.spyOn(studentService, 'addStudentToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ subject });
        comp.ngOnInit();

        expect(studentService.query).toHaveBeenCalled();
        expect(studentService.addStudentToCollectionIfMissing).toHaveBeenCalledWith(studentCollection, ...additionalStudents);
        expect(comp.studentsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const subject: ISubject = { id: 456 };
        const student: IStudent = { id: 35430 };
        subject.student = student;

        activatedRoute.data = of({ subject });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(subject));
        expect(comp.studentsSharedCollection).toContain(student);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Subject>>();
        const subject = { id: 123 };
        jest.spyOn(subjectService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ subject });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: subject }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(subjectService.update).toHaveBeenCalledWith(subject);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Subject>>();
        const subject = new Subject();
        jest.spyOn(subjectService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ subject });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: subject }));
        saveSubject.complete();

        // THEN
        expect(subjectService.create).toHaveBeenCalledWith(subject);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Subject>>();
        const subject = { id: 123 };
        jest.spyOn(subjectService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ subject });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(subjectService.update).toHaveBeenCalledWith(subject);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackStudentById', () => {
        it('Should return tracked Student primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackStudentById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
