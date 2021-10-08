import { ISubject } from 'app/entities/subject/subject.model';

export interface IStudent {
  id?: number;
  studentname?: string;
  standard?: number;
  rollno?: number;
  age?: number;
  subjects?: ISubject[] | null;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public studentname?: string,
    public standard?: number,
    public rollno?: number,
    public age?: number,
    public subjects?: ISubject[] | null
  ) {}
}

export function getStudentIdentifier(student: IStudent): number | undefined {
  return student.id;
}
