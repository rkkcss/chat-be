import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Participant from './participant';
import ParticipantDetail from './participant-detail';
import ParticipantUpdate from './participant-update';
import ParticipantDeleteDialog from './participant-delete-dialog';

const ParticipantRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Participant />} />
    <Route path="new" element={<ParticipantUpdate />} />
    <Route path=":id">
      <Route index element={<ParticipantDetail />} />
      <Route path="edit" element={<ParticipantUpdate />} />
      <Route path="delete" element={<ParticipantDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ParticipantRoutes;
