import React, { Component } from "react";
import '../App.css';
import UserService from "../services/user.service";
import {Table} from 'react-bootstrap';
import { Redirect } from "react-router";
import '../myTable.css';

export default class UserInfo extends Component {


    constructor(props) {
        super(props);
    
        this.state = {
          content:[],
          redirect:false,
          email:""
        };
        
        this.rowClick=this.rowClick.bind(this);
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
      rowClick(userEmail){
          this.setState({email:userEmail});
          this.setState({redirect:true});
      } 
      


  render() {
    
    if(this.state.redirect) {
      return <Redirect to={{pathname: '/userhistory',state: { email: this.state.email }}} ></Redirect>;
    }
    return (
        <div>
            <h1>User Details</h1>
            <Table variant="dark">
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
                        this.state.content.map((item,i)=>(
                          
                        <tr class="trow" key={i} onClick={()=>{this.rowClick(item.email);}}>
                            <td>{item.userName}</td>
                            <td>{item.email}</td>
                            <td>{item.contact}</td>
                            <td>{item.dob}</td> 
                            <td>{item.address}</td>  
                            <td>{item.fine}</td>  
                        </tr>
                        ))
                        
                    }
                    
                </tbody>
            </Table>
            
        </div>
    );
  }
}