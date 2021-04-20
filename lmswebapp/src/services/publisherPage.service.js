import axios from "axios";
import authHeader from './auth.header';

const API_URL = "http://localhost:8080/api/search";

class AuthSearchService{
    searchBooks(keyword){
        console.log(keyword)
        return axios
        .post(API_URL +"/search-book-by-publisher", {
          keyword,
        }, { headers: authHeader() }); 
    }
}

export default new AuthSearchService();