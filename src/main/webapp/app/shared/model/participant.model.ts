import { IRoom } from 'app/shared/model/room.model';
import { IProfile } from 'app/shared/model/profile.model';

export interface IParticipant {
  id?: number;
  rooms?: IRoom[] | null;
  profile?: IProfile | null;
}

export const defaultValue: Readonly<IParticipant> = {};
