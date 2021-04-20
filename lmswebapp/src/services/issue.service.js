import axios from "axios";
import authHeader from './auth.header';
const API_URL = "http://localhost:8080/api/issue/";
class IssueService {
    issue(id) {
        console.log(authHeader())
        return axios.post(API_URL + "issue-book", {
           id
        }, { headers: authHeader() });
    }
    
    getIssues(email){
        return axios.post(API_URL + "get-issues", {
            email
         }, { headers: authHeader() });
    }

    getOTP(email){
        return axios.post(API_URL + "get-otp", {
            email
        }, { headers: authHeader() });
    }

    approveIssues(email,issueId,otp){
        return axios.post(API_URL + "approve-issues", {
            email,
            issueId,
            otp
        }, { headers: authHeader() });
    }

    bookIssues(bookId){
        return axios.post(API_URL + "book-issues", {
            bookId
        }, { headers: authHeader() });
    }


}
export default new IssueService();