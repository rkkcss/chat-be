import dayjs from 'dayjs';
import { IMessageReaction } from 'app/shared/model/message-reaction.model';
import { IUser } from 'app/shared/model/user.model';
import { IRoom } from 'app/shared/model/room.model';

export interface IMessage {
  id?: number;
  text?: string | null;
  createdDate?: string | null;
  messageReactions?: IMessageReaction[] | null;
  user?: IUser | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IMessage> = {};
