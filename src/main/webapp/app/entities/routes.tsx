import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Message from './message';
import Participant from './participant';
import Room from './room';
import Profile from './profile';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="message/*" element={<Message />} />
        <Route path="participant/*" element={<Participant />} />
        <Route path="room/*" element={<Room />} />
        <Route path="profile/*" element={<Profile />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
