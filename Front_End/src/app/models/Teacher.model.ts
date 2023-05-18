import { Image } from "./Image.models";

export interface Teacher {
  teacherId: number;
  firstName: string;
  lastName: string;
  userName: string;
  email: string;
  password: string;
  role: string;
  birthday: string;
  image: Image;
  imageStr: string;
  images: Image[];
}
