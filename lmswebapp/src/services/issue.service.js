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


}
export default new IssueService();