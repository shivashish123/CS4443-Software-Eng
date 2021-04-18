import React, { Component } from "react";
import AuthSearchService from "../services/authorPage.service"
export default class authorsearch extends Component {
  constructor(props) {
    super(props);

    this.state = {
      authorname: "pub",
    };
  }

  handleSearch(e){ 
    console.log("checked")
    AuthSearchService.searchBooks('pub')
    .then(
        response =>{
            console.log(response.data)
            console.log("success")
        }
    )
  }

  render() {
    const { authorname } = this.state;
    return (
      <div>
        <button onClick ={this.handleSearch}>on click</button>
      </div>
    );
  }
}
