import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './message-reaction.reducer';

export const MessageReactionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const messageReactionEntity = useAppSelector(state => state.messageReaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="messageReactionDetailsHeading">
          <Translate contentKey="chatBeApp.messageReaction.detail.title">MessageReaction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{messageReactionEntity.id}</dd>
          <dt>
            <span id="text">
              <Translate contentKey="chatBeApp.messageReaction.text">Text</Translate>
            </span>
          </dt>
          <dd>{messageReactionEntity.text}</dd>
          <dt>
            <Translate contentKey="chatBeApp.messageReaction.user">User</Translate>
          </dt>
          <dd>{messageReactionEntity.user ? messageReactionEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="chatBeApp.messageReaction.message">Message</Translate>
          </dt>
          <dd>{messageReactionEntity.message ? messageReactionEntity.message.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/message-reaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/message-reaction/${messageReactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MessageReactionDetail;
