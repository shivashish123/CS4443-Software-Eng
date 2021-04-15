import React, {Component} from "react"

export default class UserHistory extends Component{
    constructor(props){
        super(props);
    
    }

    render(){
        return(
            <div>          
               
                hi i am  {this.props.name}
            </div>
        )
    }
}