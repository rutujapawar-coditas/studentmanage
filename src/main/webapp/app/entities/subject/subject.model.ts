import { IStudent } from 'app/entities/student/student.model';

export interface ISubject {
  id?: number;
  subjectname?: string;
  bookname?: string;
  dailyhours?: number;
  student?: IStudent | null;
}

export class Subject implements ISubject {
  constructor(
    public id?: number,
    public subjectname?: string,
    public bookname?: string,
    public dailyhours?: number,
    public student?: IStudent | null
  ) {}
}

export function getSubjectIdentifier(subject: ISubject): number | undefined {
  return subject.id;
}
