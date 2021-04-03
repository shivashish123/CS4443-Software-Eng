import logo from './logo.svg';
import './App.css';
import Home from './components/home.component';
import Login from './components/login.component';
import SignUp from './components/signup.component';

import {
  BrowserRouter as Router,
  Switch,
  Route

} from "react-router-dom";


function App() {
  return (
    <Router>
    <div className="App">
      <header className="App-header">
        <Home/>
      </header>
    </div>   
    <div className="container mt-3">
          <Switch>          
            <Route exact path="/login" component={Login} />
            <Route exact path="/signup" component={SignUp} />
          </Switch>
    </div>
    </Router>
  );
}

export default App;
