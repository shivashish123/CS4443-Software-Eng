import React, { Component } from "react";
import '../App.css';
import UserService from "../services/user.service";
import {Table} from 'react-bootstrap';

export default class UserInfo extends Component {


    constructor(props) {
        super(props);
    
        this.state = {
          content: []
        };
      }
    
      componentDidMount() {
        UserService.info().then(
          response => {
            console.log(response.data)
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
        <div>
            <h1>User Details</h1>
            <Table striped variant="dark">
                <tbody>
                    <tr>
                        <td>Name</td>
                        <td>Email-ID</td>
                        <td>Contact No</td>
                        <td>Date of Birth</td>
                        <td>Address</td>
                        <td>Fine</td>
                    </tr>
                    {
                        this.state.content.map((item,i)=>
                        <tr key={i}>
                            <td>{item.userName}</td>
                            <td>{item.email}</td>
                            <td>{item.contact}</td>
                            <td>{item.dob}</td> 
                            <td>{item.address}</td>  
                            <td>{item.fine}</td>  
                        </tr>
                        )

                    }
                    
                </tbody>
            </Table>

        </div>
    );
  }
}