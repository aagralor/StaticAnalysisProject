import React, { Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { getCode } from "../../selectors/github"
import { storeCode } from "../../actions/store-code";
// import { githubUrlAccessToken } from "./apis/urls";
import logo from '../../logo.svg';
import './github-page.css';

class GithubPage extends Component {

// // POST request using fetch with error handling
//   generateUrl = code => 
//     `${githubUrlAccessToken}?client_id=Iv1.9ad3617300b9f691&client_secret=d59cbb6f3c4f09e858f9a9a7ad9b309dfd8da700&code=${code}&redirect_uri=http://localhost:3000`;
//     // `${githubUrlAccessToken}?client_id=Iv1.9ad3617300b9f691&client_secret=d59cbb6f3c4f09e858f9a9a7ad9b309dfd8da700&code=${code}`;

  doPost(code) {
    const requestOptions = {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify({ code }),
    };
    fetch('/api/github/accesstoken/', requestOptions)
    .then(async response => {
      const data = await response.json();
      debugger;
      // check for error response
      if (!response.ok) {
        // get error message from body or default to response status
        const error = (data && data.message) || response.status;
        return Promise.reject(error);
      }
    })
  }
//   async doPost2(code) {
//     debugger;
//     const requestOptions = {
//       method: 'POST',
//       headers: { 
//         'Content-Type': 'application/json',
//         'Accept': 'application/json',
//         'Access-Control-Allow-Origin': '*',
//         'Access-Control-Request-Method': 'POST',
//         'Access-Control-Allow-Headers': 'accept, content-type',
//         'Access-Control-Max-Age': '1728000'
//       },
//       // body: {},
//       body: JSON.stringify({ title: 'React POST Request Example' })
//     };
//     // Default options are marked with *
//     const response = await fetch(this.generateUrl(code), requestOptions);
    
//     const ret =  response.json(); // parses JSON response into native JavaScript objects
//     debugger;
//   }

  render() {
    console.log(this.props);

    const codeInSearch = new URLSearchParams(this.props.location.search).get("code");
    
    if (!this.props.code) {
      this.props.setCode(codeInSearch);
    }

    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Welcome to Spring Boot React Starter!</h2>
        </div>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
        <p>
          We're going to now talk to the GitHub API. Ready?
          {/* <a href="https://github.com/login/oauth/authorize?client_id=Iv1.9ad3617300b9f691">Click here</a> to begin! */}
          {/* <a href="https://github.com/login/oauth/authorize?client_id=Iv1.9ad3617300b9f691&redirect_uri=http://localhost:3000">Click here</a> to begin! */}
          <a href="https://github.com/login/oauth/authorize?client_id=Iv1.9ad3617300b9f691&redirect_uri=http://localhost:8080/github">Click here</a> to begin!
        </p>
        {
          this.props.code &&
          <p>
            You have acquired the code({this.props.code}), to get your access code click <button onClick={() => this.doPost(this.props.code)}>HERE</button>.
          </p>
        }
      </div>
    );
  }
}

GithubPage.propTypes = {
  match: PropTypes.object,
  history: PropTypes.object,
  location: PropTypes.object,
  search: PropTypes.object,
  code: PropTypes.string,
  setCode: PropTypes.func,
}

const mapStateToPropsActions = state => ({
  code: getCode(state),
});

const mapDispatchToPropsActions = dispatch => ({
  setCode: code => dispatch(storeCode(code)),
});

export default withRouter(connect(mapStateToPropsActions, mapDispatchToPropsActions)(GithubPage));
