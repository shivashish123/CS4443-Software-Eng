import React, { Component } from "react";
import { Link } from 'react-router-dom';

import UserService from "../services/user.service";
import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem, ButtonGroup } from 'reactstrap';

export default class BoardAdmin extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: "",
      showStaff: false,
      showFine: false,
    };
    this.toggleStaffMenu = this.toggleStaffMenu.bind(this);
    this.toggleFineMenu = this.toggleFineMenu.bind(this);
  }
  
  toggleStaffMenu() {
    this.setState({ showStaff: !this.state.showStaff });
  }

  toggleFineMenu() {
    this.setState({ showFine: !this.state.showFine });
  }

  componentDidMount() {
    UserService.getAdminBoard().then(
      response => {
        this.setState({
          content: response.data,
          
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
      <div>
        <ButtonGroup class="button-grp">
        <Dropdown  isOpen={this.state.showStaff} toggle={this.toggleStaffMenu} >
          <DropdownToggle caret color="success" size="lg">
            Staff
          </DropdownToggle>
          <DropdownMenu>
            <DropdownItem tag={Link} to="/addstaff">Add Staff</DropdownItem>
            <DropdownItem tag={Link} to="/removestaff">Remove Staff</DropdownItem>
            <DropdownItem tag={Link} to="/">Staff Info</DropdownItem>
          </DropdownMenu>
        </Dropdown>
        &nbsp;&nbsp;&nbsp;
        <Dropdown isOpen={this.state.showFine} toggle={this.toggleFineMenu}>
          <DropdownToggle caret color="success" size="lg">
            Fine Rules
          </DropdownToggle>
          <DropdownMenu>
            <DropdownItem tag={Link} to="/">View Rules</DropdownItem>
            <DropdownItem tag={Link} to="/">Update Rules</DropdownItem>
          </DropdownMenu>
        </Dropdown>
        </ButtonGroup>
        &nbsp;&nbsp;&nbsp;
        <button class="menuAD2">
          User History
        </button>
        &nbsp;&nbsp;&nbsp;
        <button class="menuAD2">
          Add Admin
        </button>
        
        <div className="container">
          <button className="menuAD"> Issue Book</button>&nbsp;&nbsp;&nbsp;
          <button className="menuAD"><a href="/addBook">Add a book</a></button>&nbsp;&nbsp;&nbsp;
          <button className="menuAD"><a href="/removeBook"> Remove a book</a></button>&nbsp;&nbsp;&nbsp;
          <button className="menuAD" > Place order for book</button>
        </div>
      </div>
    );
  }
}