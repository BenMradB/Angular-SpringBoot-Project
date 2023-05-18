import { Speciality } from "./Speciality.model";
import { Teacher } from "./Teacher.model";

export interface Course {
  courseId: number;
  courseName: string;
  courseDesc: string;
  teacher: Teacher;
  speciality: Speciality;
}
