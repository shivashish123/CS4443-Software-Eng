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
}

export default new PageService();