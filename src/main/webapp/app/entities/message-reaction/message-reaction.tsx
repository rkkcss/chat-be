import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMessageReaction } from 'app/shared/model/message-reaction.model';
import { getEntities } from './message-reaction.reducer';

export const MessageReaction = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const messageReactionList = useAppSelector(state => state.messageReaction.entities);
  const loading = useAppSelector(state => state.messageReaction.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="message-reaction-heading" data-cy="MessageReactionHeading">
        <Translate contentKey="chatBeApp.messageReaction.home.title">Message Reactions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="chatBeApp.messageReaction.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/message-reaction/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="chatBeApp.messageReaction.home.createLabel">Create new Message Reaction</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {messageReactionList && messageReactionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="chatBeApp.messageReaction.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="chatBeApp.messageReaction.text">Text</Translate>
                </th>
                <th>
                  <Translate contentKey="chatBeApp.messageReaction.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="chatBeApp.messageReaction.message">Message</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {messageReactionList.map((messageReaction, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/message-reaction/${messageReaction.id}`} color="link" size="sm">
                      {messageReaction.id}
                    </Button>
                  </td>
                  <td>{messageReaction.text}</td>
                  <td>{messageReaction.user ? messageReaction.user.id : ''}</td>
                  <td>
                    {messageReaction.message ? <Link to={`/message/${messageReaction.message.id}`}>{messageReaction.message.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/message-reaction/${messageReaction.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/message-reaction/${messageReaction.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/message-reaction/${messageReaction.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="chatBeApp.messageReaction.home.notFound">No Message Reactions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MessageReaction;
