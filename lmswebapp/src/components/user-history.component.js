import React, { Component } from "react";
import '../App.css';


export default class UserHistory extends Component {


    constructor(props){
        super(props);
    
    }

    render(){
        return(
            <div>         
                <h1>{this.props.location.state.email}</h1>
            </div>
        )
    }
}