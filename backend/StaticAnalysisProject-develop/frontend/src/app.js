import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
// import logo from './logo.svg';
import './app.css';
import MainPage from "./main-page.js";

class App extends Component {

  renderGithubHome = () => <MainPage/>

  // renderCustomersContainer = () => (<h1>Customers Container</h1>)

  // renderCustomerContainer = props => <CustomerContainer /* {...props} */ dni={props.match.params.dni} />

  // renderCustomerNewContainer = () => (<h1>Customer New Container</h1>)

  render() {
    console.log(this.props);
    return (
      <Router className="router">
        <div className="App">
          <Route exact path="/" render={this.renderGithubHome} />
          {/* <Route exact path="/customers" component={CustomersContainer} />
          <Switch>
            <Route path="/customers/new" component={NewCustomerContainer} />
            <Route path="/customers/:dni" render={this.renderCustomerContainer} />
          </Switch>
          <Switch>
            <Route exact path="/customers/new" component={this.renderCustomerNewContainer} />
            <Route exact path="/customers/:dni" component={this.renderCustomerContainer} />
            <Route exact path="/customers" component={this.renderCustomersContainer} />
            <Route exact path="/" component={this.renderHome} />
          </Switch> */}
        </div>
      </Router>      
    );
  }
}

export default App;
