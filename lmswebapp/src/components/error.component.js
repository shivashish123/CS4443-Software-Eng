import React, {Component} from "react"

export default class ErrorComponent extends Component{
    constructor(props){
        super(props)
    }

    render(){
        return(
            <div>          
                <h1>You are not authorized to view that page</h1>
            </div>
        )
    }
}


