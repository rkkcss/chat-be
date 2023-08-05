import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IParticipant } from 'app/shared/model/participant.model';
import { getEntities } from './participant.reducer';

export const Participant = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const participantList = useAppSelector(state => state.participant.entities);
  const loading = useAppSelector(state => state.participant.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="participant-heading" data-cy="ParticipantHeading">
        <Translate contentKey="chatBeApp.participant.home.title">Participants</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="chatBeApp.participant.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/participant/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="chatBeApp.participant.home.createLabel">Create new Participant</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {participantList && participantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="chatBeApp.participant.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="chatBeApp.participant.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="chatBeApp.participant.room">Room</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {participantList.map((participant, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/participant/${participant.id}`} color="link" size="sm">
                      {participant.id}
                    </Button>
                  </td>
                  <td>{participant.user ? participant.user.id : ''}</td>
                  <td>{participant.room ? <Link to={`/room/${participant.room.id}`}>{participant.room.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/participant/${participant.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/participant/${participant.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/participant/${participant.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="chatBeApp.participant.home.notFound">No Participants found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Participant;
