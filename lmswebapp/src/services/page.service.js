import axios from 'axios';
import authHeader from './auth.header';

const API_URL = 'http://localhost:8080/api/webpage/';

class PageService {
  getPublicContent() {
    return axios.get(API_URL + 'all')      
  }

  getAdminPage() {
    return axios.get(API_URL + 'admin', { headers: authHeader() })
  }  

  getAddBookPage() {
    return axios.get(API_URL + 'addBook', { headers: authHeader() })
  }  

  getRemoveBookPage() {
    return axios.get(API_URL + 'removeBook', { headers: authHeader() })
  }  
  
  getAddCopiesPage(){
    return axios.get(API_URL + 'addCopies', { headers: authHeader() })
  }

  getApproveIssuePage(){
    return axios.get(API_URL + 'approveIssues', { headers: authHeader() })
  }

  getBookIssuePage(){
    return axios.get(API_URL + 'bookIssues', { headers: authHeader() })
  }

}

export default new PageService();