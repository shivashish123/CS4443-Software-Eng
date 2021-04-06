import React, { Component } from "react";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import Select from 'react-select';
import Form from "react-validation/build/form";
import Searchservice from "../services/search.service"

const sortByOptions = [
    { value: 'Rating', label: 'Rating' },
    { value: 'Popularity', label: 'Popularity' },
    { value: 'A-Z', label: 'Alpha' }
];

export default class SearchBar extends Component {
    constructor(props){
        super(props)
        this.onChangeKeyword = this.onChangeKeyword.bind(this);
        this.onChangeSortBy  = this.onChangeSortBy.bind(this);
        this.handleSearch = this.handleSearch.bind(this);

        this.state = {
            keyword: "",
            sortBy: "Alpha",
            message: "",
            successful: false
        };
    }
 

    onChangeKeyword(e) {
        this.setState({
          keyword: e.target.value
        });
    }

    onChangeSortBy = selectedOption => {
        this.setState({ selectedOption });
        console.log(`Option selected:`, selectedOption);
    };

    handleSearch(e){
        e.preventDefault();

        this.setState({
          message: "",
          successful: false
        });

        console.log("checked")

        if (this.checkBtn.context._errors.length === 0) {
             
            Searchservice.searchBooks(
              this.state.keyword,
              this.state.sortBy
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
                <Form
                    onSubmit={this.handleSearch}
                    ref={c => {
                        this.form = c;
                    }}
                >
                <div class="searchBar">                                
                 <Input
                    type="text"
                    className="form-control"
                    name="keyword"
                    placholder="Enter a keyword"
                    value={this.state.keyword}
                    onChange={this.onChangeKeyword}
                  />              
                
                  <Select
                    value={this.sortBy}
                    onChange={this.onChangeSortBy}
                    options={sortByOptions}
                 />
                 <CheckButton
                    style={{ display: "block" }}
                    ref={c => {
                    this.checkBtn = c;
                    }}
                 /> 
                 </div>
            </Form>            
            </div>
        );
    }

}