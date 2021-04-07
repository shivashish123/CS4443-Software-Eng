import React, {Component} from "react"
import ForgotService from "../services/forgot.service";

import Otp from "./otp"
export default class Forgot extends Component{
    constructor(props){
        super(props);

        this.state={
            emailid: "",
            otpform: false
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
            }
        )
    } 
   
    render(){
        return(
            <div>
                { this.state.otpform ===false &&
                <div>
                    <div >
                    <div className="  offset-md-3">
                        <form onSubmit={this.handleSubmit}>
                            <label>Email ID</label><br />
                            <input type="text" value={this.state.emailid} onChange={this.handleChange} /><br />
                            <input type="submit" />
                        </form>

                        
                    </div>
                    
                </div> 
            </div>
            }
            {
                this.state.otpform === true && <div> 
                   <Otp /> </div>
            }
            </div>
        )
        
    }
}
