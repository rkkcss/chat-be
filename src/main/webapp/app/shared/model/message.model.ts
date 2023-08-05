import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IRoom } from 'app/shared/model/room.model';

export interface IMessage {
  id?: number;
  text?: string | null;
  createdDate?: string | null;
  user?: IUser | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IMessage> = {};
