import React, { Component } from "react";
import Input from "react-validation/build/input";
import Button from 'react-bootstrap/Button'
import Form from "react-validation/build/form";
import StaffService from "../services/staff.service";
import CheckButton from "react-validation/build/button";


export default class RemoveStaff extends Component {
    constructor(props){
        super(props)
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.handleRemoveStaff = this.handleRemoveStaff.bind(this);

        this.state = {
            email: "",
            message: "",
            successful: false
        };
    }
 

    onChangeEmail(e) {
        this.setState({
          email: e.target.value
        });
    }

    handleRemoveStaff(e){
        e.preventDefault();
        this.setState({
          message: "",
          successful: false
        });
        console.log("remove staff")

        if (this.checkBtn.context._errors.length === 0) {  

            StaffService.remove(
                this.state.email
            ).then(
                response => {
                this.setState({
                    message: response.data.message,
                    successful: true
                });
                },
                error => {
                const resMessage =
                    (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                    error.message ||
                    error.toString();
        
                this.setState({
                    successful: false,
                    message: resMessage
                });
                }
            );
        } 
    }

    render(){
        return(
            <div>
              <h3 class="SerachHeading">Remove Staff Member</h3>
                <Form
                    onSubmit={this.handleRemoveStaff}
                    ref={c => {
                        this.form = c;
                    }}
                >              
                <div class="removeStaff">
                 <Input
                    type="text"
                    name="email"
                    placeholder="Enter email ID of the staff"
                    value={this.state.email}
                    onChange={this.onChangeEmail}
                    style={{width: "400px"}}
                  />   
                  &nbsp;&nbsp;&nbsp;
                    <Button variant="secondary" type="submit" size="large">Remove Staff</Button>
                 </div>
                 {this.state.message && (
                    <div className="form-group">
                        <div
                        className={
                            this.state.successful
                            ? "alert alert-success"
                            : "alert alert-danger"
                        }
                        role="alert"
                        >
                        {this.state.message}
                        </div>
                    </div>
                    )}
                 <CheckButton
                    style={{ display: "none" }}
                    ref={c => {
                        this.checkBtn = c;
                    }}
                    />
            </Form>            
            </div>
        );
    }

}