import React, { Component } from "react";

import UserService from "../services/user.service";
import Dropdown from 'react-dropdown';

export default class BoardAdmin extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: ""
    };
  }

  componentDidMount() {
    UserService.getAdminBoard().then(
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
    return (
      <div className="container">
        <button> Issue Book</button>
        <button> Add a book</button>
        <button> Remove a book</button>
        <button> Place order for book</button>
      </div>
    );
  }
}