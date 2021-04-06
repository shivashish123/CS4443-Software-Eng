import React, {Component} from "react"
import SearchBar from "./searchbar.component"

export default class Home extends Component{
    constructor(props){
        super(props)
    }

    render(){
        return(
            <div>          
            <div>
                <SearchBar/>
            </div>
            </div>
        )
    }
}


