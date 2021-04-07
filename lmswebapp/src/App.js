import React, { Component } from "react";
import { BrowserRouter as Router,Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";
import Forgot from "./components/forgot";
import Login from "./components/login.component";
import Signup from "./components/signup.component";
import Home from "./components/home.component";
import Profile from "./components/profile.component";
import BoardAdmin from "./components/board-admin.component";
import BoardUser from "./components/board-user.component";
import AddBook from "./components/addBook.component"
class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      showUserBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        currentUser: user,
        showUserBoard: user.role.includes("ROLE_USER"),
        showAdminBoard: user.role.includes("ROLE_ADMIN"),
      });
    }
  }

  logOut() {
    AuthService.logout();
  }

  render() {
    const { currentUser, showUserBoard, showAdminBoard } = this.state;

    return (
      <Router>
        <div>
          <nav className="navbar navbar-expand navbar-dark bg-dark">
            <Link to={"/"} className="navbar-brand">
              LMS
            </Link>
            <div className="navbar-nav mr-auto">
              <li className="nav-item">
                <Link to={"/home"} className="nav-link">
                  Home
                </Link>
              </li>

              {showUserBoard && (
                <li className="nav-item">
                  <Link to={"/user"} className="nav-link">
                    User DashBoard
                  </Link>
                </li>
              )}

              {showAdminBoard && (
                <li className="nav-item">
                  <Link to={"/admin"} className="nav-link">
                    Admin DashBoard
                  </Link>
                </li>
              )}

            </div>

            {currentUser ? (
              <div className="navbar-nav ml-auto">
                <li className="nav-item">
                  <Link to={"/profile"} className="nav-link">
                    {currentUser.username}
                  </Link>
                </li>
                <li className="nav-item">
                  <a href="/login" className="nav-link" onClick={this.logOut}>
                    LogOut
                  </a>
                </li>
              </div>
            ) : (
              <div className="navbar-nav ml-auto">
                <li className="nav-item">
                  <Link to={"/login"} className="nav-link">
                    Login
                  </Link>
                </li>

                <li className="nav-item">
                  <Link to={"/signup"} className="nav-link">
                    Sign Up
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to={"/forgot"} className="nav-link">
                    Forgot Password
                  </Link>
                </li>
              </div>
            )}
          </nav>

          <div className="container mt-3">
            <Switch>
              <Route exact path={["/", "/home"]} component={Home} />
              <Route exact path="/login" component={Login} />
              <Route exact path="/signup" component={Signup} />
              <Route exact path="/profile" component={Profile} />
              <Route exact path="/admin" component={BoardAdmin} />
              <Route exact path="/user" component={BoardUser} />
              <Route exact path="/addbook" component={AddBook} />
              <Route exact path="/forgot" component={Forgot} />
            </Switch>
          </div>
        </div>
      </Router>
    );
  }
}

export default App;
