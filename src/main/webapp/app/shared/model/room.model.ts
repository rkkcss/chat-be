import dayjs from 'dayjs';
import { IMessage } from 'app/shared/model/message.model';
import { IParticipant } from 'app/shared/model/participant.model';

export interface IRoom {
  id?: number;
  name?: string | null;
  createdDate?: string | null;
  message?: IMessage | null;
  participant?: IParticipant | null;
}

export const defaultValue: Readonly<IRoom> = {};
