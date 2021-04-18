import React, { Component } from "react";
import '../App.css';
import UserService from "../services/user.service";
import {Table} from 'react-bootstrap';
import { Redirect } from "react-router";
import '../myTable.css';

import Form from "react-validation/build/form";
import Button from 'react-bootstrap/Button'
import Input from "react-validation/build/input";

export default class UserInfo extends Component {


    constructor(props) {
        super(props);
    
        this.state = {
          content:[],
          redirect:false,
          email:"",
          id:""
        };
        
        this.rowClick=this.rowClick.bind(this);
        this.handleSubmit=this.handleSubmit.bind(this);
        this.onChangeId = this.onChangeId.bind(this);
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

      onChangeId(e) {
        this.setState({
          id: e.target.value
        });
      }

      rowClick(userEmail){
          this.setState({email:userEmail});
          this.setState({redirect:true});
      } 
      handleSubmit(){
        this.setState({email:this.state.id});
        this.setState({redirect:true});
      }
      


  render() {
    
    if(this.state.redirect) {
      return <Redirect to={{pathname: '/userhistory',state: { email: this.state.email }}} ></Redirect>;
    }
    return (
        <div>
            <h1>User Details</h1>
            <div>
            <Form onSubmit={this.handleSubmit}>    
                    <div class="removeBook">
                    <Input
                        type="text"
                        placeholder="Enter email id of person"
                        value={this.state.id}
                        onChange={this.onChangeId}
                        style={{width: "400px"}}
                    />   
                    &nbsp;&nbsp;&nbsp;
                        <Button variant="info" type="submit" size="large">Submit</Button>
                     </div> 
                </Form>
                </div>
                <br/>   
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