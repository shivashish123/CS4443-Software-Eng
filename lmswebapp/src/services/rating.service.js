import axios from "axios";
import authHeader from './auth.header';

const API_URL = "http://localhost:8080/api/book";

class RatingService{
    send(rating,review,bookid){
        return axios
        .post(API_URL +"/add-rating", {
          rating,review,bookid
        }, { headers: authHeader() }); 

    }
}

export default new RatingService();
