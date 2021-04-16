import React, { Component } from "react";
import BookTile from "./book.component";

export default class GridComponent extends Component {

    constructor(props) {
        super(props);           
    }

    render(){
        const { list} = this.props;
        console.log(list);
        console.log(list[0].authors[0].authorName);
        console.log("grid component")
        // var imgData = "data:image/png;base64," + list[0].content
        // var imgData2 = "data:image/png;base64," + list[1].content
        return(         

            <div className="flexbox1">
                 {list.map((x) => (
                            <div key={x.id}>                               
                            <BookTile imgData={"data:image/png;base64,"+x.content} title={x.title}/>
                            </div>
                        )
                    )}  
            </div>
        )
    }

}