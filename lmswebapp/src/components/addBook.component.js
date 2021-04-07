import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import { useState } from "react";

const required = value => {
    if (!value) {
      return (
        <div className="alert alert-danger" role="alert">
          This field is required!
        </div>
      );
    }
};

export default class AddBook extends Component {
    constructor(props){
        super(props)
        this.onChangeTitle = this.onChangeTitle.bind(this)

        this.state = {
           title : "" ,
           inputList : [{ name :"" }]
          };
          
    }

    onChangeTitle(e) {
        this.setState({
          title: e.target.value
        });
    }

    onChangeName(e,idx) {        
        this.state.inputList[idx]= e.target.value        
    }

    render(){
        return(
            <div className="col-md-12">
                <div className="card card-container">
                    <Form
                    onSubmit={this.handleLogin}
                    ref={c => {
                        this.form = c;
                    }}
                    >
                    <div className="form-group">
                        <label htmlFor="Book Title">Book Title</label>
                        <Input
                        type="text"
                        className="form-control"
                        name="book Title"
                        value={this.state.title}
                        onChange={this.onChangeTitle}
                        validations={[required]}
                        />
                    </div>
                        {this.state.inputList.map((x, i) => {
                            <Input
                            type="text"
                            className="form-control"
                            name="Author"
                            value={x.name}                           
                            validations={[required]}
                            />
                        })
                        }
                  
                    </Form>
                </div>
              </div>
        );
    }
}