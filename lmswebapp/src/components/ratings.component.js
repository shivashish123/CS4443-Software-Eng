import React, { Component } from 'react'
import {
    Card, CardText, CardBody, CardLink,
    CardTitle, CardSubtitle, Button,Col, Row, CardHeader,Table
  } from 'reactstrap';
import {  Form, FormGroup, Label, Input, FormText } from 'reactstrap';
import StarRatings from 'react-star-ratings';
import { useHistory } from "react-router-dom";
import RatingService from '../services/rating.service'
export default class Rating extends Component {
    constructor(props){
        super(props)
        this.state={
            rating:0,
            review:"",
            content:"",
            success:false

        }
        this.changeRating=this.changeRating.bind(this)
        this.handleSubmitClick=this.handlSubmitClick.bind(this)
        this.onChangeReview=this.onChangeReview.bind(this)
    }
    changeRating( newRating ) {
        
        this.setState({
          rating: newRating
        });
    }
    onChangeReview(e){
        e.preventDefault();
        this.setState({
            review:e.target.value
        })
    }

    handlSubmitClick(){
        RatingService.send(this.state.rating,this.state.review,this.props.location.state.id).then(
            response => {
                console.log(response.data)
                this.setState({
                  
                  success:true
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
        )
    
    }

    render() {
        return (<>
            {!this.state.success && <div className="container">
                
                
                <Row>
                <Col sm="7" >
                <Card body outline color="secondary" sm="7" >
                 <CardHeader>Rate/Review</CardHeader>

                <CardBody>
                Book ID: &nbsp;{this.props.location.state.id}
                 
                <div className="card">
                
                <h5>Rate </h5> 
                <StarRatings
                rating={this.state.rating}
                starRatedColor="rgb(218,165,32)"
                starHoverColor="green"
                changeRating={this.changeRating}
                numberOfStars={5}
                name='rating'
                starDimension="30px"
                starSpacing="15px"
                starEmptyColor="rgb(193, 215, 215)"
                > </StarRatings>
               
                </div>
                
                <div className="card">
                <h5> Review</h5>
                <Form>
                <FormGroup>
                <Label for="exampleText"></Label>
                <Input type="textarea" name="text" id="exampleText" onChange={this.onChangeReview}/>
                </FormGroup>
                </Form>
                </div>
                <Button color ="secondary" onClick={this.handleSubmitClick}>Submit</Button>
                </CardBody>    
                </Card>
                </Col>
                
                </Row>

            </div>
          }
          {
            this.state.success && <div ><h2>Book rated successfully</h2> </div>
          }
          </>
        )
    }
}
