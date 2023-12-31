import message from 'app/entities/message/message.reducer';
import participant from 'app/entities/participant/participant.reducer';
import room from 'app/entities/room/room.reducer';
import profile from 'app/entities/profile/profile.reducer';
import messageReaction from 'app/entities/message-reaction/message-reaction.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  message,
  participant,
  room,
  profile,
  messageReaction,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
