import React, { Component } from 'react'
import ForgotService from "../services/forgot.service";
import Passwordreset from "./passwordreset"
export default class Otp extends Component {
    constructor(props){
        super(props);

        this.state={
            otp:"",
            passform: false,
            success: true,
            message: ""
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        this.setState({otp : e.target.value});
    }
    handleSubmit(e){
        e.preventDefault();
        this.setState({passform :true});
        console.log(this.state.otp);
        ForgotService.sendotp(this.state.otp)       
        .then(
            (res)=>{
                console.log(res);
            }
        )
        .catch(
            (error)=>{
                console.log(error);
                const resMessage =
                    (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                    error.message ||
                    error.toString();

                    this.setState({
                    success: false,
                    message: resMessage
                  });
            }
        )
    } 
    render() {
        return (
            <div>
               {this.state.passform === false &&
                   <div className="container">
                 <div className="  offset-md-3">
                    <form onSubmit={this.handleSubmit}>
                        <label>Enter the OTP</label><br />
                        <input type="text" value={this.state.otp} onChange={this.handleChange} /><br />
                        <input type="submit" />
                    </form>

                    
                </div>
                
                </div> } 
                {
                    this.state.success && this.state.passform === true && <div>< Passwordreset token={this.state.otp} /></div>
                }
                  { !this.state.success &&
                    (
              <div className="form-group">
                  <br /><br /><br />
                <div
                  className={
                    this.state.success
                      ? "alert alert-success"
                      : "alert alert-danger"
                  }
                  role="alert"
                >
                  {this.state.message}
                </div>
              </div>
            )}
            </div>
        )
    }
}
