import React, { Component } from "react";
import '../App.css';
import StaffService from "../services/staff.service";
import {Table} from 'react-bootstrap';


export default class StaffInfo extends Component {


    constructor(props) {
        super(props);
    
        this.state = {
          content: []
        };
      }
    
      componentDidMount() {
        StaffService.info().then(
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
        <div>
            <h1>Staff Member Details</h1>
            <Table striped variant="dark">
                <tbody>
                    <tr>
                        <td>Name</td>
                        <td>Email-ID</td>
                        <td>Contact No</td>
                        <td>Date of Birth</td>
                        <td>Address</td>
                    </tr>
                    {
                        this.state.content.map((item,i)=>
                        <tr key={i} >
                            <td>{item.userName}</td>
                            <td>{item.email}</td>
                            <td>{item.contact}</td>
                            <td>{item.dob}</td> 
                            <td>{item.address}</td>  
                        </tr>
                        )

                    }
                    
                </tbody>
            </Table>

        </div>
    );
  }
}