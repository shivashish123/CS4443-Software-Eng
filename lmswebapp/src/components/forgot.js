import React, {Component} from "react"
import ForgotService from "../services/forgot.service";
import { isEmail } from "validator";
import Otp from "./otp"

const required = value => {
    if (!value) {
      return (
        <div className="alert alert-danger" role="alert">
          This field is required!
        </div>
      );
    }
  };
  
  const email = value => {
    if (!isEmail(value)) {
      return (
        <div className="alert alert-danger" role="alert">
          This is not a valid email.
        </div>
      );
    }
  };
export default class Forgot extends Component{
    constructor(props){
        super(props);

        this.state={
            emailid: "",
            otpform: false,
            success: true,
            message: ""
        };
        
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    handleChange(e) {
        this.setState({emailid : e.target.value});
      }
    handleSubmit(e){
        e.preventDefault();
        this.setState( {otpform: true});
        ForgotService.forgot(this.state.emailid)       
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
                    
                    this.setState( {otpform: false});
                    this.setState({
                    success: false,
                    message: resMessage
                  });
            }
        )
    } 
   
    render(){
        return(
            <div>
                { this.state.otpform ===false &&
                <div>
                    <div  className="card"  style={{ width: '30rem' }}>
                    <div className="card-body" >
                        <form >
                            <div className="form-group"> 
                            <label>Email ID</label>
                            <input className="form-control"type="text" value={this.state.emailid} onChange={this.handleChange} validations={[required, email]}/>
                            <small id="emailHelp" class="form-text text-muted">Otp will be sent to the mail to reset password.</small>
                        
                            <div className="btn-group btn-group-toggle" data-toggle="buttons">
                            
                            <label className="btn btn-secondary">
                             <input type="radio" name="options" id="option3" autoComplete="off" onClick={this.handleSubmit}/> Submit
                            </label>
                            </div>
                            </div>
                        </form>   
                    </div>
                    </div> 
            </div>
            }
            {   
                this.state.success && this.state.otpform === true && <div> 
                   <Otp /> 
                   </div>
                   
             }       
                   { !this.state.success &&
                    (
              <div className="form-group">
                  
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

                   <div>
                   </div>
            
            </div>
        )
        
    }
}

