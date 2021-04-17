import React, {Component} from "react"
import Bookpage from "./book-page.component";
export default class BookPageMain extends Component{
    constructor(props){
        super(props)
        console.log(this.props.location.state)
    }

    render(){
        return(
            <div>
                <Bookpage book={this.props.location.state}/>
            </div>
        )
    }
}


