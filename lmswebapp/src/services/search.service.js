import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/search";

class SearchService{
    searchBooks(keyword,searchBy,sortBy){
        console.log(keyword)
        console.log(searchBy)
        console.log(sortBy)
        return axios
        .post(API_URL , {
          keyword,
          searchBy,
          sortBy
        })       
    }
}

export default new SearchService();