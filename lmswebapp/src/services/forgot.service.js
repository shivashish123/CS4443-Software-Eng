import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/forgot-password";

class ForgotService {
  forgot(emailid) {
    console.log(emailid)
    return axios
      .post(API_URL , {
        emailid
      })
      .then(response => {
        return response.data;
      });
  }  
}

export default new ForgotService();