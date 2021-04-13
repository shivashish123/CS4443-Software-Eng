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
                   <div className="card" style={{ width: '15rem' }}>
                    <div className="card-body">
                    <form >
                        <label>Enter the OTP</label><br />
                        <input className ="form-control "type="text" value={this.state.otp} onChange={this.handleChange} />
                        
                        <div className="btn-group btn-group-toggle" data-toggle="buttons">
                            
                            <label className="btn btn-secondary">
                             <input type="radio" name="options" id="option3" autocomplete="off" onClick={this.handleSubmit}/> Submit
                            </label>
                            </div>
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
