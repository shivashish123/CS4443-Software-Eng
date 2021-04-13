import React, { Component } from "react";

export default class GridComponent extends Component {

    constructor(props) {
        super(props);               
       
    }

    render(){
        const { list} = this.props;
        console.log(list);
        // var imgData = "data:image/png;base64," + list[0].content
        // var imgData2 = "data:image/png;base64," + list[1].content
        return(
            <div>
           {/* <img id="ItemPreview" src={imgData}/>    
           <img id="ItemPreview" src={imgData2}/>       */}
            </div>
        )
    }

}