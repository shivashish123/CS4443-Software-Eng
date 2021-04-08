import axios from "axios";
import authHeader from './auth.header';
const API_URL = "http://localhost:8080/api/auth/";
class AddBookService {
    addBook( title,authorList,publisher,copies,genre,subGenre) {
      console.log(authorList)
      console.log(genre)
      console.log(subGenre)
      var authors = []
      authorList.map((x)=>{
        authors.push(x.value)
      });
      console.log(authors);
      return axios.post(API_URL + "add-book",{
        title,
        authors,
        publisher,
        copies,
        genre,
        subGenre
      }, { headers: authHeader() });
    }
}

export default new AddBookService();