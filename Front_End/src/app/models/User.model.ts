import { Role } from "./Roles.models";

export interface User {
  user_id: number;
  username: string;
  password: string;
  enabled: Boolean
  roles: Role[];
}
