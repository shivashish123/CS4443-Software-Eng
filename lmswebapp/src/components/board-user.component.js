import React, { Component } from "react";
import logo from '../user-icon.png';
import { Container, Row, Col } from 'reactstrap';
import UserService from "../services/user.service";
import PeopleCard from './book-card';

export default class BoardUser extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: "",
      people: [
        {
          id: 1,
          name: "David Davidson",
          company: "Some Company, Inc",
          description: "Met at a party. Will connect with later"
        },
        {
          id: 2,
          name: "Mark Markson",
          company: "Some Company, Inc",
          description: "Met at a party. Will connect with later"
        },
        {
          id: 3,
          name: "Judy Judyson",
          company: "Some Company, Inc",
          description: "Met at a party. Will connect with later"
        },
        {
          id: 4,
          name: "James Jameson",
          company: "Some Company, Inc",
          description: "Met at a party. Will connect with later"
        }
      ]
    };
  }

  componentDidMount() {
    UserService.getUser().then(
      response => {
        this.setState({
          content: response.data
        });
      },
      error => {
        this.setState({
          content:
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString()
        });
      }
    );
  }

  render() {
    let peopleCards = this.state.people.map(person => {
      return (
        <Col sm="4">
          <PeopleCard key={person.id} person={person} />
        </Col>
      )
    })
    return (
      /*<div className="container">
        <header className="jumbotron">
          <h3>{JSON.stringify(this.state.content.email)}</h3>
        </header>
      </div>*/

    <div>
      <div className="col-md-16">
        <div className="card card-container">
          <img
            src={logo}
            alt="profile-img"
            className="profile-img-card"
          />
          <h5>Name:{this.state.content.userName}</h5>
          <h5>Email ID: {this.state.content.email}</h5>
          <h5>Fine: </h5>
        </div>
      </div>
      <Container fluid>
        <Row>
          {peopleCards}
        </Row>
      </Container>
    </div>
    );
  }
}