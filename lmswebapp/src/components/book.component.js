import React, { Component } from 'react';
import logo from '../user-icon.png'
import { Card, CardImg, CardText, CardBody, CardTitle, CardSubtitle, Button } from 'reactstrap';

class BookTile extends Component {
  constructor(props) {
    super(props);
  }
  render () {
    let { title, imgData } = this.props;
    return (
        <div className="flexbox2">
            <div className="bookCard card-container">
            <img
                src={imgData}
                alt="cover-photo"
                className="bookCover"
            />
            <div className="bookTitle">{title}</div>
            </div>
         </div>
    )
  }
}

export default BookTile;