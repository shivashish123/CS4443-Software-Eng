import React, { Component } from 'react';
import logo from '../user-icon.png'
import { Card, CardImg, CardText, CardBody, CardTitle, CardSubtitle, Button } from 'reactstrap';

class PeopleCard extends Component {
  constructor(props) {
    super(props);
  }
  render () {
    let { id, name, company, description } = this.props.person;
    return (
      <div>
        <Card>
          <CardImg top width="50%" src={logo} alt="Card image cap" />
          <CardBody>
            <CardTitle>{name}</CardTitle>
            <CardSubtitle>{company}</CardSubtitle>
            <CardText>{description}</CardText>
            <Button color="danger" onClick={() => this.props.removePerson(id)}>Delete</Button>
          </CardBody>
        </Card>
      </div>
    )
  }
}

export default PeopleCard;