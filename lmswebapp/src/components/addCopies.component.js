import React, { Component } from "react";
import Input from "react-validation/build/input";
import Button from 'react-bootstrap/Button'
import Form from "react-validation/build/form";
import BookService from "../services/book.service";
import CheckButton from "react-validation/build/button";
import PageService from "../services/page.service";
import ErrorComponent from "./error.component";

export default class AddCopies extends Component {
    constructor(props){
        super(props)
        this.onChangeId = this.onChangeId.bind(this);
        this.handleAddCopies = this.handleAddCopies.bind(this);
        this.onChangeNumber = this.onChangeNumber.bind(this);
        this.state = {
            id: "",
            message: "",
            number : 0 ,
            successful: false
        };
    }
 

    onChangeId(e) {
        this.setState({
          id: e.target.value
        });
    }

    onChangeNumber(e) {
        this.setState({
          number: e.target.value
        });
    }

    handleAddCopies(e){
        e.preventDefault();
        this.setState({
          message: "",
          successful: false
        });
        console.log("add copies")

        if (this.checkBtn.context._errors.length === 0) {  

            BookService.addCopies(
                this.state.id,
                this.state.number
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

    componentDidMount() {
        PageService.getAddCopiesPage().then(
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
                <h3 class="SerachHeading">Add Copies</h3>
                    <Form
                        onSubmit={this.handleAddCopies}
                        ref={c => {
                            this.form = c;
                        }}
                    >              
                    <div class="addCopies">
                    <Input
                        type="text"
                        placeholder="Enter id of the book"
                        value={this.state.id}
                        onChange={this.onChangeId}
                        style={{width: "400px"}}
                    /> 
                    <Input
                        type="number"
                        placeholder="Enter total copies"
                        value={this.state.number}
                        onChange={this.onChangeNumber}
                        style={{width: "400px"}}
                    />   
                    &nbsp;&nbsp;&nbsp;
                        <Button variant="secondary" type="submit" size="large">Add Copies</Button>
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
}