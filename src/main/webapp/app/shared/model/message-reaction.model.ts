import { IUser } from 'app/shared/model/user.model';
import { IMessage } from 'app/shared/model/message.model';

export interface IMessageReaction {
  id?: number;
  text?: string | null;
  user?: IUser | null;
  message?: IMessage | null;
}

export const defaultValue: Readonly<IMessageReaction> = {};
