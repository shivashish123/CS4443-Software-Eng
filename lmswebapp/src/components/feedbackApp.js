import React from 'react';

import FeedBack from './Feedback';
import '../App.css';
const API_URL = "http://localhost:8080/api/auth/";
function FeedBackApp() {
	return (
		<div className="App">
			<FeedBack
				style={{zIndex:'2', marginLeft:'20px', position:'fixed'}}
				position="right"
				numberOfStars={5}
				headerText="Hello"
				bodyText="Have Feedback? Please help us improve."
				buttonText="Feedback"
				handleClose={() => console.log("handleclose")}
				handleSubmit={(data) => 
					fetch(API_URL+"savefeedback", {
						headers: {
							Accept: 'application/json',
							'Content-Type': 'application/json'
						},
						method: 'POST', // or 'PUT'
						body: JSON.stringify(data),
					}).then((response) => { 
						if (!response.ok) {
							return Promise.reject('Our servers are having issues! We couldn\'t send your feedback!');
						}
						response.json()
					}).then(() => {
						alert('Thank you for you feedback!');
					}).catch((error) => {
						alert('Our servers are having issues! We couldn\'t send your feedback!', error);
					})
				}
				handleButtonClick={() => console.log("handleButtonClick")}
			/>
		</div>
	);
}

export default FeedBackApp;