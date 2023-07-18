import dayjs from 'dayjs';
import { IRoom } from 'app/shared/model/room.model';
import { IProfile } from 'app/shared/model/profile.model';

export interface IMessage {
  id?: number;
  text?: string | null;
  createdDate?: string | null;
  rooms?: IRoom[] | null;
  profile?: IProfile | null;
}

export const defaultValue: Readonly<IMessage> = {};
