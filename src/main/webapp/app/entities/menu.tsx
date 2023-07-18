import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/message">
        <Translate contentKey="global.menu.entities.message" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/participant">
        <Translate contentKey="global.menu.entities.participant" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/room">
        <Translate contentKey="global.menu.entities.room" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/profile">
        <Translate contentKey="global.menu.entities.profile" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
