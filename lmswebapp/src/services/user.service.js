import axios from 'axios';
import authHeader from './auth.header';

const API_URL = 'http://localhost:8080/api/auth/';

class UserService {

  info(){
    return axios.post(API_URL + "info-user", {
    }, { headers: authHeader() });
  }

  getUser(){
    const email = JSON.parse(localStorage.getItem('user')).email;
    return axios.post(API_URL + "user-details", {
      email
    },{ headers: authHeader() });
  }
  getMyHistory(email){
    return axios.post(API_URL + "get-user-history", { email
    }, { headers: authHeader() });
  }
}

export default new UserService();