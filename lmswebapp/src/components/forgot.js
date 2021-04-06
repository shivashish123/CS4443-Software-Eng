import axios from "axios";
import React, {Component} from "react"


export default class Forgot extends Component{
    constructor(props){
        super(props);

        this.state={
            emailid: ""
        };
        this.handleChange = this.handleChange.bind(this);
    }
    handleChange(e) {
        this.setState({emailid : e.target.value});
      }
    handleSubmit(e){
        e.preventDefault()
        axios.post("http://localhost:8080/api/forgot-password",e.target.value)
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
                
            <div classname="container">
                 <div className=" offset-md-3">
                    <form onSubmit={this.handleSubmit}>
                        <label>Email ID</label><br />
                        <input type="text" value={this.state.emailid} onChange={this.handleChange} /><br />
                        <input type="submit" />
                    </form>
                
                </div>
            </div>    
        </div>
        )
    }
}

