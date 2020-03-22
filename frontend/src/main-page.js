import React, { Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { getCode } from "./selectors/github"
import { storeCode } from "./actions/storeCode";
import logo from './logo.svg';
import './main-page.css';

class MainPage extends Component {
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
          <a href="https://github.com/login/oauth/authorize?client_id=Iv1.9ad3617300b9f691">Click here</a> to begin!
          {/* <a href="https://github.com/login/oauth/authorize?client_id=Iv1.9ad3617300b9f691&redirect_uri=http://localhost:3000">Click here</a> to begin! */}
        </p>
        {
          this.props.code &&
          <p>
            You have acquired the code, to get your access code click here.
          </p>
        }
        <p>
          If that link doesn't work, remember to provide your own <a href="/apps/building-oauth-apps/authorizing-oauth-apps/">Client ID</a>!
        </p>
      </div>
    );
  }
}

MainPage.propTypes = {
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

export default withRouter(connect(mapStateToPropsActions, mapDispatchToPropsActions)(MainPage));
