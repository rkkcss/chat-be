import { IUser } from 'app/shared/model/user.model';

export interface IProfile {
  id?: number;
  phoneNumber?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IProfile> = {};
