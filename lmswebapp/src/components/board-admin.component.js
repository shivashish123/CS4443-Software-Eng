import React, { Component } from "react";
import { Link } from 'react-router-dom';

import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem, ButtonGroup ,Button } from 'reactstrap';
import PageService from "../services/page.service";
import ErrorComponent from "./error.component";
export default class BoardAdmin extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: "",
      showStaff: false,
      showFine: false,
      authorized:0
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
    PageService.getAdminPage().then(
      response => {
        console.log("authorized")
        this.setState({
          authorized : 2
        });
      },
      error => {
        console.log("error")
        this.setState({
          content:
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString() ,
          authorized : 1
        });
      }
    );
  }

  render() {  
    if(this.state.authorized === 1){
      return(
        <ErrorComponent/>
      )
    }
    else if(this.state.authorized == 0){
      return(
        <div></div>
      )
    }
    else {
    return (
      <div>      
        <div>
        <ButtonGroup class="button-grp">
        <Dropdown  isOpen={this.state.showStaff} toggle={this.toggleStaffMenu} >
          <DropdownToggle caret color="success" size="lg">
            Staff
          </DropdownToggle>
          <DropdownMenu>
            <DropdownItem tag={Link} to="/addstaff">Add Staff</DropdownItem>
            <DropdownItem tag={Link} to="/removestaff">Remove Staff</DropdownItem>
            <DropdownItem tag={Link} to="/infostaff">Staff Info</DropdownItem>
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
        <Link to="/infouser">
          <button class="menuAD2">
            User History
          </button>
        </Link>
        
        &nbsp;&nbsp;&nbsp;
        <Link to="/addadmin">
        <button type="button" class="menuAD2">Add Admin</button>
        </Link>
        
        <div className="container">
          <Button className="menuAD" tag={Link} to="/addbook">Add a book</Button>&nbsp;&nbsp;&nbsp;
          <Button className="menuAD" tag={Link} to="/removebook">Remove a book</Button>&nbsp;&nbsp;&nbsp;
          <button className="menuAD" > Place order for book</button>&nbsp;&nbsp;&nbsp;
          <Button className="menuAD" tag={Link} to="/addcopies">Add copies</Button>&nbsp;&nbsp;&nbsp;
          <Button className="menuAD" tag={Link} to="/approveissues">Approve Issue</Button>&nbsp;&nbsp;&nbsp;
          <Button className="menuAD" tag={Link} to="/bookissues">Book Issue</Button>&nbsp;&nbsp;&nbsp;
        </div>
        </div>        
      </div>
    );
    }
  }
}