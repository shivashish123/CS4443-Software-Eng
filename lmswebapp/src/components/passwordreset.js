import React, { Component } from 'react'
import ForgotService from "../services/forgot.service";
export default class Passwordreset extends Component {
    constructor (props){
        super(props);

        this.state={

            pass: "",
            token: props.otp,
            confirm_pass: ""
        };

        this.handlepass1Change = this.handlepass1Change.bind(this);
        this.handlepass2Change = this.handlepass2Change.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handlepass1Change(e) {
        this.setState({pass : e.target.value});
      }
    handlepass2Change(e) {
        this.setState({confirm_pass : e.target.value});
    }  
    handleSubmit(e){
        e.preventDefault()
        if(this.state.pass === this.state.confirm_pass ){

            ForgotService.change_pass(this.state.password)       
        .then(
            (res)=>{
                console.log(res);
            }
        )
        .catch(
            (error)=>{
                console.log(error);
            }
        )
        }
        
        
    } 
    render() {
        return(
            <div>
                
            <div classname="container">
                 <div className=" offset-md-3">
                    <form onSubmit={this.handleSubmit}>
                        <label>Please Enter the new password</label><br />
                        New Password  <br/>
                        <input type="password" value={this.state.pass} onChange={this.handlepass1Change} /><br />
                        Confirm New Password <br/>
                        <input type="password" value={this.state.confirm_pass} onChange={this.handlepass2Change} /><br />
                        <input type="submit" value="Change Password"/>
                    </form>
                
                </div>
                
            </div>    
        </div>
        )
    }
}
