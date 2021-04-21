import React, { Component } from "react";
import BookTile from "./book.component";
import Pagination from "./pagination.component"

export default class GridComponent extends Component {

    constructor(props) {
        super(props);  
        this.state = { allBooks: [], currentBooks: [], currentPage: null, totalPages: null }

    }

    componentDidMount() {
        const { list: allBooks = [] } = this.props;
        this.setState({ allBooks });
    }

    onPageChanged = data => {
        const { allBooks } = this.state;
        const { currentPage, totalPages, pageLimit } = data;
        const offset = (currentPage - 1) * pageLimit;
        const currentBooks = allBooks.slice(offset, offset + pageLimit);

        this.setState({ currentPage, currentBooks, totalPages });
    }         

    render(){
        const totalBooks = this.state.allBooks.length;
        console.log(this.state.currentBooks)
        if(this.state.allBooks.length>0){
            return(         
                <div>
                    <div className="flexbox3">
                        <h2>
                            <strong className="text-secondary">{totalBooks}</strong> Books
                        </h2>
                        <div className="float-right">
                        { this.state.currentPage && (
                            <span>
                            Page <span className="font-weight-bold">{ this.state.currentPage }</span> / <span className="font-weight-bold">{ this.state.totalPages }</span>
                            </span>
                        )}
                        </div>
                        <div>                      
                        <Pagination totalRecords={totalBooks} pageLimit={3} pageNeighbours={1} onPageChanged={this.onPageChanged} />
                        </div>
                    </div>
                    <div className="flexbox1">
                        { this.state.currentBooks.map(item => <BookTile key={item.bookId} book={item} imgData={"data:image/png;base64,"+item.content} />) }
                    </div>
                </div>
            )
        }
        else{
            return(
                <h3 className="centerAlign">No matching books found !!</h3>
            )
        }
    };
}