import React, {Component} from "react"
import SearchBar from "./searchbar.component"

export default class Home extends Component{
    constructor(props){
        super(props)
    }

    render(){
        return(
            <div>
            <div class = "homepage-header">
                <h1>LMS Website</h1>                
                <span class="homepage-login-register">
                    <a href="/login"> Login </a>  | <a href="/signup"> SignUp </a>
                </span>                
            </div>
            <div>
                <SearchBar/>
            </div>
            </div>
        )
    }
}


