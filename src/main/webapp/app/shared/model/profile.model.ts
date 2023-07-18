import { IUser } from 'app/shared/model/user.model';
import { IParticipant } from 'app/shared/model/participant.model';
import { IMessage } from 'app/shared/model/message.model';

export interface IProfile {
  id?: number;
  phoneNumber?: string | null;
  user?: IUser | null;
  participants?: IParticipant[] | null;
  messages?: IMessage[] | null;
}

export const defaultValue: Readonly<IProfile> = {};
