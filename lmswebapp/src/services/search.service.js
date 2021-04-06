import axios from "axios";

const API_URL = "http://localhost:8080/api/search/";

class SearchService{
    searchBooks(keyword,sortBy){
        console.log(keyword)
        console.log(sortBy)
        return axios
        .post(API_URL , {
          keyword,
          sortBy
        })       
    }
}

export default new SearchService();