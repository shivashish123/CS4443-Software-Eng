import React, { Component } from "react";
import Input from "react-validation/build/input";
import Button from 'react-bootstrap/Button'
import Form from "react-validation/build/form";
import BookService from "../services/book.service";
import CheckButton from "react-validation/build/button";
import PageService from "../services/page.service";
import ErrorComponent from "./error.component";
import issueService from "../services/issue.service";

export default class ApproveIssue extends Component {
    constructor(props){
        super(props)
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.handleApproveIssue = this.handleApproveIssue.bind(this);

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

    handleApproveIssue(e){
        e.preventDefault();
        this.setState({
          message: "",
          successful: false
        });

        if (this.checkBtn.context._errors.length === 0) {  

            issueService.getIssues(
                this.state.email
            ).then(
                response => {
                console.log(response)
                this.setState({
                    successful: true,
                    issues : response.data
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

    componentDidMount() {
        PageService.getApproveIssuePage().then(
          response => {
            console.log("authorized")
            this.setState({
              authorized : 2
            });
          },
          error => {
            console.log("error")
            this.setState({
              content:
                (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
                error.message ||
                error.toString() ,
              authorized : 1
            });
          }
        );
      }
    
      render() {  
        if(this.state.authorized === 1){
          return(
            <ErrorComponent/>
          )
        }
        else if(this.state.authorized == 0){
          return(
            <div></div>
          )
        }
        else {  
            return(
                
                <div>
                <h3 class="SerachHeading">Approve Issue Request</h3>
                    <Form
                        onSubmit={this.handleApproveIssue}
                        ref={c => {
                            this.form = c;
                        }}
                    >    
                     {!this.state.successful && (
                    <div class="ApproveIssue">
                    <Input
                        type="text"
                        placeholder="Enter email of the issuer"
                        value={this.state.email}
                        onChange={this.onChangeEmail}
                        style={{width: "400px"}}
                    />   
                    &nbsp;&nbsp;&nbsp;
                        <Button variant="secondary" type="submit">Go</Button>
                     </div>)} 
                    {this.state.message && (
                        <div>list of all issues</div>
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
}