import dayjs from 'dayjs';
import { IMessage } from 'app/shared/model/message.model';
import { IParticipant } from 'app/shared/model/participant.model';

export interface IRoom {
  id?: number;
  name?: string | null;
  createdDate?: string | null;
  lastMessage?: IMessage | null;
  messages?: IMessage[] | null;
  participants?: IParticipant[] | null;
}

export const defaultValue: Readonly<IRoom> = {};
