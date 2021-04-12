import axios from "axios";
import authHeader from './auth.header';

const API_URL = "http://localhost:8080/api/search";

class SearchService{
    searchBooks(keyword,searchBy,sortBy){
        console.log(keyword)
        console.log(searchBy)
        console.log(sortBy)
        return axios
        .post(API_URL +"/search-book", {
          keyword,
          searchBy,
          sortBy
        }, { headers: authHeader() }); 
    }
}

export default new SearchService();