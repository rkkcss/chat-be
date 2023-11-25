import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MessageReaction from './message-reaction';
import MessageReactionDetail from './message-reaction-detail';
import MessageReactionUpdate from './message-reaction-update';
import MessageReactionDeleteDialog from './message-reaction-delete-dialog';

const MessageReactionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MessageReaction />} />
    <Route path="new" element={<MessageReactionUpdate />} />
    <Route path=":id">
      <Route index element={<MessageReactionDetail />} />
      <Route path="edit" element={<MessageReactionUpdate />} />
      <Route path="delete" element={<MessageReactionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MessageReactionRoutes;
