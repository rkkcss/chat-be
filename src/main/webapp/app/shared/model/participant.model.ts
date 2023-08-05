import { IUser } from 'app/shared/model/user.model';
import { IRoom } from 'app/shared/model/room.model';

export interface IParticipant {
  id?: number;
  user?: IUser | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IParticipant> = {};
