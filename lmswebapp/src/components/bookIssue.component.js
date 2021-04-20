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

export default class BookIssue extends Component {
    constructor(props){
        super(props)
        this.onChangeBookid = this.onChangeBookid.bind(this);
        this.getBookIssues = this.getBookIssues.bind(this);

        this.state = {
            bookid: "",
            message: "",
            successful: false,
            content: ""
        };
    }
 

    onChangeBookid(e) {
      this.setState({
        bookid: e.target.value
      });
    }

  

    getBookIssues(e){
      e.preventDefault();
      console.log("clicked")
      issueService.bookIssues(
        this.state.bookid
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

    componentDidMount() {
        PageService.getBookIssuePage().then(
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
                <h3 class="SerachHeading">Book Issues</h3>
                    <Form
                        onSubmit={this.getBookIssues}
                        ref={c => {
                            this.form = c;
                        }}
                    >    
                     {(
                    <div class="ApproveIssue">
                    <Input
                        type="text"
                        placeholder="Enter book id"
                        value={this.state.email}
                        onChange={this.onChangeBookid}
                        style={{width: "400px"}}
                    />   
                    &nbsp;&nbsp;&nbsp;
                        <Button variant="secondary" type="submit">Go</Button>
                     </div>)} 
                    {!this.state.successful && this.state.message &&  
                    (<div className="form-group">
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
                    </div>)}
                    {this.state.successful && !this.state.content.length  && ( <h5 className="centerAlign marign-top-15">No user has issued this book</h5>)}
                    {this.state.successful && (this.state.content.length > 0) &&  (
                        <div>
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
                    </div>
                    )}  
                </Form>                 
                </div>
            );
        }
    }
}