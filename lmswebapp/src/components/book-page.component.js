import React, { Component } from 'react'
import logo from "../book.png"
import flame from "../flame.svg"
import StarRatings from 'react-star-ratings';
import Ratings from 'react-ratings-declarative';
import { IoRocketOutline } from 'react-icons/io5';
import {
    Card, CardText, CardBody, CardLink,
    CardTitle, CardSubtitle, Button,Col, Row, CardHeader,Table
  } from 'reactstrap';
import { RatingIcon } from 'semantic-ui-react';
import IssueService from '../services/issue.service';
export default class Bookpage extends Component {

    constructor(props){
        super(props);
        console.log(this.props)
        this.state={           
            book : this.props.book,
            content:"",
            success:true
        }
        this.handleissueClick=this.handleissueClick.bind(this)
    }
    handleissueClick(e){
        e.preventDefault();
        IssueService.issue(this.state.book.bookId).then(
            response => {
              console.log(response.data)
              this.setState({
                content: response.data,
                
              });
            },
            error => {
              this.setState({
                success:false,
                content:
                  (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                  error.message ||
                  error.toString()
              });
            }
          );
        
    }


    render() {
        return (
            <div >
                <Row >
                <Col sm="5" >
                <Card body outline color="secondary">
                    <CardHeader>{this.state.book.title} </CardHeader>
        <CardBody>
          <CardTitle tag="h5">Book</CardTitle>
          
        
        <img width="30%" src={this.state.book.imgData} alt="Card image cap" />
        
            <Card body outline color="secondary">
            <CardText>
            Book ID : {this.state.book.bookId}<br/><br/>
            
            <div> Ratings &nbsp; &nbsp; 
            <StarRatings
                rating={this.state.book.rating}
                starRatedColor="green"
                changeRating={this.changeRating}
                numberOfStars={5}
                name='rating'
                starDimension="30px"
                starSpacing="5px"
                starEmptyColor="rgb(193, 215, 215)"
            /> 
            </div><br/>
            <div> Popularity &nbsp; &nbsp; 
            <Ratings
            rating={this.state.book.popularity}
            widgetRatedColors="rgb(204, 0, 0)"
            changeRating={this.changeRating}
            widgetSpacings="5px"
            widgetDimensions="30px"
            widgetEmptyColors="rgb(236, 198, 198)"
            svgIconViewBoxes="0 0 16 16"
            svgIconPaths="M5.016 16c-1.066-2.219-.498-3.49.321-4.688c.897-1.312 1.129-2.61 1.129-2.61s.706.917.423 2.352c1.246-1.387 1.482-3.598 1.293-4.445c2.817 1.969 4.021 6.232 2.399 9.392c8.631-4.883 2.147-12.19 1.018-13.013c.376.823.448 2.216-.313 2.893C9.999 1.002 6.818.002 6.818.002c.376 2.516-1.364 5.268-3.042 7.324c-.059-1.003-.122-1.696-.649-2.656c-.118 1.823-1.511 3.309-1.889 5.135c-.511 2.473.383 4.284 3.777 6.197z"
            >
                <Ratings.Widget />
            <Ratings.Widget />
            <Ratings.Widget />
            <Ratings.Widget/>
            <Ratings.Widget />
            </Ratings>
           </div>
            </CardText>
            </Card>
        </CardBody>
      </Card>
      </Col>
      <Col sm="7">
      
      <Card body outline color="secondary">
      <CardHeader>Book Description</CardHeader>
          <CardBody>
          <Table>
            <tbody>
                
                Authors : &nbsp; 
                {
                    this.state.book.authors.map((item,i)=>(
                      item.authorName
                    
                    ))
                }
                
            </tbody>

          </Table>
          </CardBody>    
      </Card>
      </Col>
      </Row>
        <Button color="secondary" size="large" onClick={this.handleissueClick}>Issue Book</Button>&nbsp;
        {
         !this.state.success &&
         (
          <div className="form-group">
              
            <div
              className={
                this.state.success
                  ? "alert alert-success"
                  : "alert alert-danger"
              }
              role="alert"
            >
              {this.state.content}
            </div>
          </div>
        )
        }
        <Button color="secondary" size="large">Reserve Book</Button>
        <hr color="black"></hr>
     </div>
        )
    }
}
