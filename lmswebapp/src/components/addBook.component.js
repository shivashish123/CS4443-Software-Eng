import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import Button from "react-validation/build/button";
import { useState } from "react";
import AddBookService from "../services/addBook.service";
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
        this.onChangeName = this.onChangeName.bind(this)
        this.onAddItem = this.onAddItem.bind(this)
        this.state = {
           title : "" ,
           list : [{ name :"mike" },{ name :"virat" }]
          };
          
    }

    onChangeTitle(e) {
        this.setState({
          title: e.target.value
        });
    }

    onChangeName(e,idx) {  
        console.log(idx)
        const list = [...this.state.list];
        list[idx]["name"] =  e.target.value;
        return{
            list
        };
    }
    onAddItem = () => {
        this.setState(state => {
          const list = [...state.list, {name:""}];
          return {
            list
          };
        });
    };
    handleSubmit(e){
        e.preventDefault();
        AddBookService.addBook()
    }

    render(){
        return(
            <div className="col-md-12">
                <div className="card card-container">
                    <Form
                    onSubmit={this.handleSubmit}
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
                        {this.state.list.map((x, i) => (

                            <div class="flexbox1">
                                <Input
                                type="text"
                                className="form-control"
                                name="Author"
                                value={x.name}   
                                onChange={(e)=>this.onChangeName(e,i)}                        
                                validations={[required]}
                                />    
                                
                                <div >
                                    {i === 0 && <button type="button" onClick={this.onAddItem}>Add</button>}
                                    {i !== 0 && <button type="button" class="hidden" >Add</button>}
                               
                                </div>
                            </div>

                        ))}
                    <button type="submit">Add Book</button>
                    </Form>
                    <div style={{ marginTop: 20 }}>{JSON.stringify(this.state.list)}</div>
                </div>
              </div>
        );
    }
}