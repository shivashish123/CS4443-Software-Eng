import axios from "axios";
import authHeader from './auth.header';
const API_URL = "http://localhost:8080/api/book/";
class BookService {
    addBook( data) {
      return axios.post(API_URL + "add-book",
          data
      , { headers: authHeader() ,  "Content-Type": "multipart/form-data" });
    }

    removeBook(id){
      console.log(id);
      return axios.post(API_URL + "remove-book",{
        id
      }, { headers: authHeader() });
    }

    addCopies(id,count){
      console.log(id);
      return axios.post(API_URL + "add-copies",{
        id,
        count
      }, { headers: authHeader() });
    }

    showBook(id){
      console.log(id);
      return axios
      .post(API_URL +"image", {
        id
      }, { headers: authHeader() }); 
    }

    
}

export default new BookService();