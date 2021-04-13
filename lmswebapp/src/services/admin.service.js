import axios from "axios";
import authHeader from './auth.header';

const API_URL = "http://localhost:8080/api/auth/";

class AdminService {

  register(username, email,password, address,contact,dob) {
    return axios.post(API_URL + "add-admin",{
      username,
      email,
      password,
      address,
      contact,
      dob
    }, { headers: authHeader() });
  }

  remove(email) {
    return axios.post(API_URL + "remove-staff", {
      email
    }, { headers: authHeader() });
  }
}

export default new AdminService();