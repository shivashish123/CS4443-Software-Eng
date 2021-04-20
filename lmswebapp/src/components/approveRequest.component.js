import React, { Component } from "react";
import Input from "react-validation/build/input";
import Button from 'react-bootstrap/Button'
import Form from "react-validation/build/form";
import BookService from "../services/book.service";
import CheckButton from "react-validation/build/button";
import PageService from "../services/page.service";
import ErrorComponent from "./error.component";
import issueService from "../services/issue.service";
import {Table} from 'react-bootstrap';

export default class ApproveIssue extends Component {
    constructor(props){
        super(props)
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.handleApproveIssue = this.handleApproveIssue.bind(this);
        this.onSendOTP = this.onSendOTP.bind(this);
        this.getIssues = this.getIssues.bind(this);
        this.onChangeOTP = this.onChangeOTP.bind(this);

        this.state = {
            email: "",
            message: "",
            successful: false,
            otpError : false,            
            otpId: "",
            otp:""
        };
    }
 

    onChangeEmail(e) {
      this.setState({
        email: e.target.value
      });
    }

    onChangeOTP(e){
      this.setState({
        otp: e.target.value
      });      
    }

    onSendOTP(e){
      issueService.getOTP(this.state.email).then(
        response => {
          console.log(response)
          this.setState({
              otpSuccessfull: true,
              otpId : response.data
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
            otpSuccessfull: false,
            message: resMessage
        });
      
      });
    }

    getIssues(e){
      issueService.getIssues(
        this.state.email
      ).then(
          response => {
          console.log(response)
          this.setState({
              successful: true,
              content : response.data
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


    handleApproveIssue(e){
        e.preventDefault();
        console.log("clicked");

        issueService.approveIssues(this.state.email,this.state.otpId , this.state.otp).then(
          response => {
          console.log(response)
          this.setState({
              email: "",
              message: response.data.message ,
              otpError: true
          });
          setTimeout(window.location.reload(),3);
          },
          error => {
          const resMessage =
              (error.response &&
              error.response.data &&
              error.response.data.message) ||
              error.message ||
              error.toString();

          this.setState({
              message: resMessage
          });
          }
      );
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
                     {(!this.state.successful || this.state.approved) && (
                    <div class="ApproveIssue">
                    <Input
                        type="text"
                        placeholder="Enter email of the issuer"
                        value={this.state.email}
                        onChange={this.onChangeEmail}
                        style={{width: "400px"}}
                    />   
                    &nbsp;&nbsp;&nbsp;
                        <Button variant="secondary" type="button" onClick={this.getIssues} >Go</Button>
                     </div>)} 
                    
                    {this.state.successful && !this.state.content.length  && ( <h5 className="centerAlign marign-top-15">No pending approve requests</h5>)}
                    {this.state.successful && (this.state.content.length > 0) &&  (
                        <div>
                        <h1>User Issues</h1>
                        <Table variant="dark">
                            <tbody>
                                <tr>
                                    <td>IssueId</td>
                                    <td>UserName</td>
                                    <td>Email</td>
                                    <td>Book-ID</td>
                                    <td>Title</td>
                                    <td>Issue Date</td>
                                    <td>Due Date</td>
                                </tr>
                                {   
                                    this.state.content.map((item,i)=>(
                                      
                                    <tr class="trow" key={i}>
                                        <td>{item.issueId}</td>
                                        <td>{item.username}</td>
                                        <td>{item.email}</td>
                                        <td>{item.bookId}</td> 
                                        <td>{item.title}</td>  
                                        <td>{item.issueDate}</td>
                                        <td>{item.returnDate}</td>  
                                    </tr>
                                    ))
                                    
                                }
                                
                            </tbody>
                        </Table>   

                        <div>
                        <Button 
                            variant="secondary" type="button"
                            onClick={this.onSendOTP}                              
                        > Send OTP </Button>
                        <Input
                            type="text"
                            placeholder="Enter User's OTP"
                            value={this.state.otp}
                            onChange={this.onChangeOTP}
                            style={{width: "400px"}}
                        />  

                          <Button 
                            variant="secondary" type="submit"
                            disabled = {!this.state.otp.length}
                        > Approve </Button>                

                        {this.state.message && (
                          <div className="form-group">
                            <div
                              className={
                                this.state.otpError
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
                        </div>
                    )}                    
                    <CheckButton
                        style={{ display: "none" }}
                        ref={c => {
                            this.checkBtn = c;
                        }}/>
                </Form>            
                </div>
            );
        }
    }
}