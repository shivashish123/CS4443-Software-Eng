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
        this.onRemoveItem = this.onRemoveItem.bind(this)
        this.onChangePublisher = this.onChangePublisher.bind(this)
        this.onChangeCopies = this.onChangeCopies.bind(this)
        this.state = {
           title : "" ,
           list : [{ name :" " }],
           publisher: "",
           copies: 0 ,
           temp :""
          };
          
    }

    onChangeTitle(e) {
        this.setState({
          title: e.target.value
        });
    }
    onChangeCopies(e) {
        this.setState({
          copies: e.target.value
        });
    }
    onChangePublisher(e) {
        this.setState({
          publisher: e.target.value
        });
    }

    onChangeName(e,idx) {     
        this.setState(state => {     
            const list = [...state.list];
            list[idx]["name"] =  e.target.value;
            return{
                list
            };
        });       
    }
    onAddItem = () => {
        this.setState(state => {
          const list = [...state.list, {name:""}];
          return {
            list
          };
        })
    }; 

    onRemoveItem = (e,idx)=>{
        this.setState(state => {
            //var list = state.list.filter((item, j) => idx !== j);      
            var list = [...state.list];
            list.splice(idx, 1);
            return {
              list
            };
          });  
          
    }
    
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
                        <label htmlFor="Author">Author</label>
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
                                    {this.state.list.length !== 1 && <button type="button"  onClick={(e)=>this.onRemoveItem(e,i)}>X</button>}                                    
                                    {this.state.list.length === 1 && <button type="button" class="hidden" >X</button>}
                                    {i === (this.state.list.length-1) && <button type="button" onClick={this.onAddItem}>Add</button>}
                                    {i !== (this.state.list.length-1) && <button type="button" class="hidden" >Add</button>}
                               
                                </div>
                            </div>

                        ))}
                         <div className="form-group">
                            <label htmlFor="Publisher">Publisher</label>
                            <Input
                            type="text"
                            className="form-control"
                            name="Publisher"
                            value={this.state.publisher}
                            onChange={this.onChangePublisher}
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="Copies">Copies</label>
                            <Input
                            type="number"
                            className="form-control"
                            name="Copies"
                            value={this.state.copies}
                            onChange={this.onChangeCopies}
                            />
                        </div>                        

                    <button type="submit">Add Book</button>
                    </Form>
                    <div style={{ marginTop: 20 }}>{JSON.stringify(this.state.list)}</div>
                    <div style={{ marginTop: 20 }}>{JSON.stringify(this.state.copies)}</div>
                </div>
              </div>
        );
    }
}