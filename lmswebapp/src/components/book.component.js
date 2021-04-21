import React, { Component } from 'react';
import logo from '../user-icon.png'
import { Card, CardImg, CardText, CardBody, CardTitle, CardSubtitle, Button } from 'reactstrap';
import bookService from '../services/book.service';
import { Redirect } from 'react-router-dom';

class BookTile extends Component {
  constructor(props) {
    super(props);
    this.redirectBookPage = this.redirectBookPage.bind(this);
    this.state = {
      redirect: null ,
      title : this.props.book.title,
      imgData : this.props.imgData,
      authors : this.props.book.authors,
      bookId:  this.props.book.bookId
    }
    console.log("props")
    
  }

  redirectBookPage(){
    console.log("clicked");
    console.log(this.props.book);
    this.setState({
      redirect: "/bookpage"
    }) 
  }
  render () {
    if (this.state.redirect) {
      //return <Redirect to={this.state.redirect} state={this.props.title} />
      return <Redirect to={{ pathname: "/bookpage", state: { title: this.state.title , authors: this.state.authors, bookId: this.state.bookId, imgData: this.state.imgData} }} />
    }
    return (
    
        <div className="flexbox2">
            <div className="bookCard card-container"
                 onClick={this.redirectBookPage} 
            >
            <img
                src={this.state.imgData}
                alt="cover-photo"
                className="bookCover"               
            />
            <div className="bookTitle">{this.state.title}</div>
            </div>
         </div>
    )
  }
}

export default BookTile;