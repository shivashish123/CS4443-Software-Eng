import axios from "axios";
import authHeader from './auth.header';
const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
  login(email, password) {
    console.log("login post")
    console.log(email)
    console.log(password)
    return axios
      .post(API_URL + "signin", {
        email,
        password
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(username, email, password,address,contact,dob) {
    return axios.post(API_URL + "signup", {
      username,
      email,
      password,
      address,
      contact,
      dob
    });
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem('user'));;
  }
}

export default new AuthService();