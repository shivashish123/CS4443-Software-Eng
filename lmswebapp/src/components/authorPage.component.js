import React, { Component } from "react";
import "../Box.css";
import logo from '../user-icon.png';
import booklogo from '../book.png'
import {  Card, CardText, CardBody, CardLink,
    CardTitle, CardSubtitle, Button,Col, Row, CardHeader } from "react-bootstrap";
const Dynamicbooks = () => {
    const cardInfo = [
      {
        bookid:1234 , 
        image: booklogo,
        booktitle: "HP1",
        popularity: 5,
      },
      {
        bookid:123  ,
        image:booklogo,
        booktitle: "HP2",
        popularity: 4,
      },
       {
        bookid:123  ,
        image:booklogo,
        booktitle: "HP3",
        popularity: 4,
      },
      
    ];
const renderCard = (card, index) => {
    return (         
      <Card style={{ width: "18rem" }} key={index} className="box" >
        <Card.Img variant="top" src="holder.js/100px180" src={card.image} />
        <Card.Body>
          <Card.Title> Book Title: {card.booktitle}</Card.Title>
          <Card.Text> Popularity of book : {card.popularity}</Card.Text>
          <Button variant="secondary" type="submit" size="large">Select Book</Button>
          
        </Card.Body>
      </Card>
    );
  };

  return(
       
  <div className="grid">{
    cardInfo.map(renderCard)
    }</div>
    )
  ;
};

export default class AuthorPage extends Component{
  constructor (props){
    super(props);
  
  this.state = {
    Authorname:"J K rolling ",
    bookList:[]
  }
}

render(){
  return(

    <div>
      <div className="col-md-16">
        <div className="card card-container">
        <img
            src={logo}
            alt="Author-img"
            className="author-img-card"
          />
          <h5>Authors Books</h5>
        </div>
      </div>
    
      <Dynamicbooks></Dynamicbooks>
    </div>
    
  );
}




}


