import React, { Component } from "react";
import '../App.css';
import UserService from "../services/user.service";
import {Table} from 'react-bootstrap';
export default class UserHistory extends Component {


    constructor(props){
        super(props);
        this.state = {
            content: []
        };  
    }

    componentDidMount() {
        UserService.getMyHistory(this.props.location.state.email).then(
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

    render(){
        return(
            <div>
                <h1>User History Details of : </h1> <h1 style={{color: "blue"}}>{this.props.location.state.email}</h1>
                <Table striped variant="dark">
                    <tbody>
                        <tr>
                            <td>Book-ID</td>
                            <td>Activity</td>
                            <td>Date of Activity</td>
                        </tr>
                        {
                            this.state.content.map((item,i)=>
                            <tr key={i} >
                                <td>{item.bookId}</td>
                                <td>{item.activity}</td>
                                <td>{item.activityDate}</td>
                            </tr>
                            )

                        }
                        
                    </tbody>
                </Table>

            </div>
        )

        
    }
}