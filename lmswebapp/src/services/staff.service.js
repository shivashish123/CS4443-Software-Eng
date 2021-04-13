import axios from "axios";
import authHeader from './auth.header';

const API_URL = "http://localhost:8080/api/auth/";

class StaffService {

  register(username, email, address,contact,dob) {
    return axios.post(API_URL + "add-staff",{
      username,
      email,
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

  info(){
    return axios.post(API_URL + "info-staff", {
    }, { headers: authHeader() });
  }
}

export default new StaffService();