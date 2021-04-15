import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import Select from 'react-select';
import BookService from '../services/book.service'
import PageService from "../services/page.service";
import ErrorComponent from "./error.component";

const required = value => {
    if (!value) {
      return (
        <div className="alert alert-danger" role="alert">
          This field is required!
        </div>
      );
    }
};

const GenreOptions = [
    { value: 'Fiction', label: 'Fiction' },
    { value: 'Mystery', label: 'Mystery' },
    { value: 'Science', label: 'Science' }
];

const SubGenreOptions = 
    {"Fiction" :[
        { value: 'Fiction1', label: 'Fiction1' },
        { value: 'Fiction2', label: 'Fiction2' },
        { value: 'Fiction3', label: 'Fiction3' }],

        "Mystery" :[
            { value: 'Mystery1', label: 'Mystery1' },
            { value: 'Mystery2', label: 'Mystery2' },
            { value: 'Mystery3', label: 'Mystery3' }]        
    }
;

export default class AddBook extends Component {
    constructor(props){
        super(props)
        this.onChangeTitle = this.onChangeTitle.bind(this)
        this.onAddItem = this.onAddItem.bind(this)
        this.onRemoveItem = this.onRemoveItem.bind(this)
        this.onChangePublisher = this.onChangePublisher.bind(this)
        this.onChangeCopies = this.onChangeCopies.bind(this)
        this.onupdateInput = this.onupdateInput.bind(this)
        this.onChangeGenre  = this.onChangeGenre.bind(this);
        this.onChangeSubGenre  = this.onChangeSubGenre.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onFileChangeHandler = this.onFileChangeHandler.bind(this);
        this.state = {
           title : "" ,
           list : [],
           publisher: "",
           copies: 0 ,
           newItem :"",
           Genre: "",
           subGenre: "",
           authorized: 0,
           selectedFile : undefined
          };
          
    }

    onChangeTitle(e) {
        this.setState({
          title: e.target.value
        });
    }
    onChangeCopies(e) {
        this.setState({
          copies: e.target.value
        });
    }
    onChangePublisher(e) {
        this.setState({
          publisher: e.target.value
        });
    }
    onupdateInput(e){
        this.setState({
            newItem: e.target.value
          });
    }

    onChangeGenre = selectedOption => {
        this.setState({ Genre :selectedOption.value });
        console.log(this.state.Genre)
    };

    onChangeSubGenre = selectedOption => {
        this.setState({ subGenre :selectedOption.value });
        console.log(this.state.subGenre)
    };
     
    onAddItem = (newValue) => {
        if(newValue !== ""){
            this.setState(state => {
            const list = [...state.list, {value:newValue,id:Date.now()}];
            return {
                list,
                newItem:""
            };
            })
        }   
    }; 

    onRemoveItem = (id)=>{
        console.log(id)
        this.setState(state => {
            const list = [...state.list];
            const updatedList = list.filter(item=> item.id !== id);
            this.setState({list:updatedList})
          });  
          
    }

    onFileChangeHandler(e){
      console.log(e.target.files[0])
      e.preventDefault();
        this.setState({
            selectedFile: e.target.files[0]
        });
 
    }
    
    handleSubmit(e){
        e.preventDefault();
        const formData = new FormData();
        var authors = [] ;
        this.state.list.map((x)=>{
          authors.push(x.value)
        });
        formData.append('title',this.state.title);
        formData.append('authors',authors);
        formData.append('publisher',this.state.publisher);
        formData.append('copies',this.state.copies);
        formData.append('genre',this.state.Genre);
        formData.append('subGenre',this.state.subGenre);
        formData.append('cover',this.state.selectedFile);
        BookService.addBook(
           formData
        ).then(
            response => {
              this.setState({
                message: response.data.message,
                successful: true
              });
            },
            error => {
              const resMessage =
                (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
                error.message ||
                error.toString();
    
              this.setState({
                successful: false,
                message: resMessage
              });
            }
          );
    }
    componentDidMount() {
        PageService.getAddBookPage().then(
          response => {
            console.log("authorized")
            this.setState({
              authorized : 2
            });
          },
          error => {
            console.log("error")
            this.setState({
              content:
                (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
                error.message ||
                error.toString() ,
              authorized : 1
            });
          }
        );
      }
    
      render() {  
        if(this.state.authorized === 1){
          return(
            <ErrorComponent/>
          )
        }
        else if(this.state.authorized == 0){
          return(
            <div></div>
          )
        }
        else {  
        return(
            <div className="col-md-12">
                <div className="card card-container">
                    <Form
                    onSubmit={this.handleSubmit}
                    ref={c => {
                        this.form = c;
                    }}
                    >
                    {!this.state.successful && (
                    <div>
                    <div className="form-group">
                        <label htmlFor="Book Title">Book Title</label>
                        <Input
                        type="text"
                        className="form-control"
                        name="book Title"
                        value={this.state.title}
                        onChange={this.onChangeTitle}
                        validations={[required]}
                        />
                    </div>
                    <label htmlFor="Author">Author</label>
                    <div class="flexbox1">
                    <input
                        type="text"
                        placeholder="Author Name"
                        className = "authorInput"
                        value = {this.state.newItem}
                        onChange = {this.onupdateInput}
                    />
                    <button
                    onClick ={()=>this.onAddItem(this.state.newItem)} 
                    disabled = {!this.state.newItem.length}
                    >
                    Add Author
                    </button>
                    </div>
                    <div>
                    {this.state.list.map((x) => (
                            <li key={x.id}>                               
                            {x.value}
                            <button
                                onClick={()=>this.onRemoveItem(x.id)}
                            > Remove
                            </button>
                            </li>
                        )
                    )}  
                    </div>                        
                    <div className="form-group">
                        <label htmlFor="Publisher">Publisher</label>
                        <Input
                        type="text"
                        className="form-control"
                        name="Publisher"
                        value={this.state.publisher}
                        onChange={this.onChangePublisher}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="Copies">Copies</label>
                        <Input
                        type="number"
                        className="form-control"
                        name="Copies"
                        value={this.state.copies}
                        onChange={this.onChangeCopies}
                        />
                    </div>  
                    
                    <div>
                    <label htmlFor="Genre">Genre</label>    
                    <Select
                        value={this.Genre}
                        className ="Genre"
                        placeholder ="Genre"
                        onChange={this.onChangeGenre}                        
                        options={GenreOptions}
                    />   
                    </div>
                    <label htmlFor="SubGenre">Sub-Genre</label>
                    <Select
                        value={this.subGenre}
                        className ="SubGenre"
                        placeholder ="SubGenre"
                        onChange={this.onChangeSubGenre}                        
                        options={SubGenreOptions[this.state.Genre]}
                    />                      
                    <label>Upload Book Cover </label>
                    <input type="file" name="file" onChange={this.onFileChangeHandler}/>
                    <br/><br/>
                    <button type="submit" 
                    disabled = {!this.state.list.length}
                    >Add Book</button>
                    </div>)}

                    {this.state.message && (
                      <div className="form-group">
                        <div
                          className={
                            this.state.successful
                              ? "alert alert-success"
                              : "alert alert-danger"
                          }
                          role="alert"
                        >
                          {this.state.message}
                        </div>
                      </div>
                    )}

                    </Form>
                    {/* <div style={{ marginTop: 20 }}>{JSON.stringify(this.state.list)}</div>
                    <div style={{ marginTop: 20 }}>{JSON.stringify(this.state.copies)}</div> */}
                </div>
              </div>
            );
        }
    }
}