import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

class ForgotService {
  forgot(emailid) {
    console.log(emailid)
    return axios
      .post(API_URL + "forgot-password" , {
        emailid
      })
      .then(response => {
        return response.data;
      });
  }
  change_pass(password){
    return axios
      .post(API_URL + "confirm-token" , {
        password
      })
      .then(response => {
        return response.data;
      });
    }
    sendotp(otp){
      return axios
        .post(API_URL + "reset-password" , {
          otp
        })
        .then(response => {
          return response.data;
        });
  
    }
  
}

export default new ForgotService();