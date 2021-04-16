import React, { Component } from "react";
import Input from "react-validation/build/input";
import Button from 'react-bootstrap/Button'
import Select from 'react-select';
import Form from "react-validation/build/form";
import Searchservice from "../services/search.service"
import GridComponent from "./grid.component"
const sortByOptions = [
    { value: 'Rating', label: 'Rating' },
    { value: 'Popularity', label: 'Popularity' },
    { value: 'A-Z', label: 'Alpha' }
];

const searchByOptions = [
  { value: 'Book', label: 'Book' },
  { value: 'Author', label: 'Author' },
  { value: 'Publisher', label: 'Publisher' }
];

export default class SearchBar extends Component {
    constructor(props){
        super(props)
        this.onChangeKeyword = this.onChangeKeyword.bind(this);
        this.onChangeSortBy  = this.onChangeSortBy.bind(this);
        this.onChangeSearchBy  = this.onChangeSearchBy.bind(this);
        this.handleSearch = this.handleSearch.bind(this);

        this.state = {
            keyword: "",
            sortBy: "Alpha",
            searchBy: "Book",
            message: "",
            successful: false,
            searchList: [],
            ratedList: [],
            trendingList: []
        };
    }
 

    onChangeKeyword(e) {
        this.setState({
          keyword: e.target.value
        });
    }

    onChangeSortBy = selectedOption => {
        this.setState({ sortBy :selectedOption.value });
        console.log(`Option selected:`, selectedOption);
    };
    onChangeSearchBy = selectedOption => {
      this.setState({ searchBy :selectedOption.value });
      console.log(`Option selected:`, selectedOption);
    };

    handleSearch(e){
        e.preventDefault();

        this.setState({
          message: "",
          successful: false
        });

        console.log("checked")

                    
            Searchservice.searchBooks(
              this.state.keyword,
              this.state.searchBy,
              this.state.sortBy,
            ).then(
              response => {
                console.log(response.data)
                console.log("success")
                this.setState({
                  searchList: response.data,
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

    render(){
        return(
            <div>
              <h3 class="SerachHeading">Search Books</h3>
                <Form
                    onSubmit={this.handleSearch}
                    ref={c => {
                        this.form = c;
                    }}
                >              
                <div class="search">
                  <div>
                 <Input
                    type="text"
                    className="searchTerm"
                    name="keyword"
                    placeholder="Enter a keyword"
                    value={this.state.keyword}
                    onChange={this.onChangeKeyword}
                  /> 
                  </div>  
                  <div>
                  <Select
                    value={this.sortBy}
                    className ="searchSelect"
                    placeholder ="Search By"
                    onChange={this.onChangeSearchBy}
                    options={searchByOptions}
                  />
                  </div>           
                  <div>
                  <Select
                    value={this.sortBy}
                    className ="searchSelect2"
                    placeholder ="Sort By"
                    onChange={this.onChangeSortBy}
                    options={sortByOptions}
                  />
                  </div>
                  <div>
                    <Button variant="secondary" type="submit">Search</Button>
                 </div>
                 </div>
            </Form>      

            {this.state.successful && (
            <div className="searchResults">              
            <h5>Search Results </h5>
            <GridComponent list={this.state.searchList}/> 
            </div> )}
            </div>

            
        );
    }

}