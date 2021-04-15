import React, { Component } from "react";
import '../App.css';
import UserService from "../services/user.service";
import {Table} from 'react-bootstrap';
import UserHistory from "./userhistory"


export default class UserInfo extends Component {


    constructor(props) {
        super(props);
    
        this.state = {
          content:[],
          history:"",
          show:false
        };
        
        this.rowClick=this.rowClick.bind(this);
        this.userhistory=this.userhistory.bind(this);
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
      userhistory(username){
        
        console.log("history");
        return (
          <div>
            <UserHistory name={username}/>
          </div>
        )
      }
      rowClick(username){
        this.setState({show:true});
        console.log(username);
        this.setState({history:this.userhistory(username)});
        
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
                        this.state.content.map((item,i)=>(
                          
                        <tr key={i} onClick={()=>{this.rowClick(item.userName);}}>
                            <td>{item.userName}</td>
                            <td>{item.email}</td>
                            <td>{item.contact}</td>
                            <td>{item.dob}</td> 
                            <td>{item.address}</td>  
                            <td>{item.fine}</td>  
                        </tr>
                        ))
                        
                    }
                    {this.state.show && <tr><td>{this.state.history}</td></tr>}
                    
                </tbody>
            </Table>
            
        </div>
    );
  }
}