import axios from 'axios';
import authHeader from './auth.header';

const API_URL = 'http://localhost:8080/api/auth/';

class UserService {

  info(){
    return axios.post(API_URL + "info-user", {
    }, { headers: authHeader() });
  }
}

export default new UserService();