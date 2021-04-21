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
import AddBook from "./components/addBook.component";
import RemoveBook from "./components/removeBook.component";
import AddStaff from "./components/add-staff.component";
import RemoveStaff from "./components/remove-staff.component";
import AddAdmin from "./components/add-admin.component";
import StaffInfo from "./components/info-staff.component";
import UserInfo from "./components/info-user.component";
import AddCopies from "./components/addCopies.component";
import FeedBackApp from "./components/feedbackApp";
import UserHistory from "./components/user-history.component";
import BookPageMain from "./components/book-page-main.component";
import AuthorPage from "./components/authorPage.component";
import PublisherPage from "./components/publisherPage.component";
import authorsearch from "./components/authorsearch.component"
import ApproveIssue from "./components/approveRequest.component";
import BookIssue from "./components/bookIssue.component";

import Rating from "./components/ratings.component"
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
          <div>
            <FeedBackApp />
          </div>

          <div className="container mt-3">
            <Switch>
              <Route exact path={["/", "/home"]} component={Home} />
              <Route exact path="/login" component={Login} />
              <Route exact path="/signup" component={Signup} />
              <Route exact path="/profile" component={Profile} />
              <Route exact path="/admin" component={BoardAdmin} />
              <Route exact path="/user" component={BoardUser} />
              <Route exact path="/userhistory" component={UserHistory} />
              <Route exact path="/addbook" component={AddBook} />
              <Route exact path="/removebook" component={RemoveBook} />
              <Route exact path="/addstaff" component={AddStaff} />
              <Route exact path="/removestaff" component={RemoveStaff} />
              <Route exact path="/infostaff" component={StaffInfo} />
              <Route exact path="/infouser" component={UserInfo} />
              <Route exact path="/forgot" component={Forgot} />
              <Route exact path="/addadmin" component={AddAdmin} />
              <Route exact path="/addcopies" component={AddCopies} />
              {/* <Route exact path="/bookpage" component={BookPageMain} /> */}
              <Route exact path="/bookpage" render={(props) => <BookPageMain {...props}/>}/>
              <Route exact path="/ratings" component={Rating} />
              <Route exact path="/authorpage" component={AuthorPage} />
              <Route exact path="/publisherpage" component={PublisherPage} />
              <Route exact path="/approveissues" component={ApproveIssue} />
              <Route exact path="/bookissues" component={BookIssue} />
            </Switch>
          </div>
        </div>
      </Router>
    );
  }
}

export default App;
