import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMessage } from 'app/shared/model/message.model';
import { getEntities } from './message.reducer';

export const Message = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const messageList = useAppSelector(state => state.message.entities);
  const loading = useAppSelector(state => state.message.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="message-heading" data-cy="MessageHeading">
        <Translate contentKey="chatBeApp.message.home.title">Messages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="chatBeApp.message.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/message/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="chatBeApp.message.home.createLabel">Create new Message</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {messageList && messageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="chatBeApp.message.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="chatBeApp.message.text">Text</Translate>
                </th>
                <th>
                  <Translate contentKey="chatBeApp.message.createdDate">Created Date</Translate>
                </th>
                <th>
                  <Translate contentKey="chatBeApp.message.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="chatBeApp.message.room">Room</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {messageList.map((message, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/message/${message.id}`} color="link" size="sm">
                      {message.id}
                    </Button>
                  </td>
                  <td>{message.text}</td>
                  <td>
                    {message.createdDate ? <TextFormat type="date" value={message.createdDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{message.user ? message.user.id : ''}</td>
                  <td>{message.room ? <Link to={`/room/${message.room.id}`}>{message.room.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/message/${message.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/message/${message.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/message/${message.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="chatBeApp.message.home.notFound">No Messages found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Message;
