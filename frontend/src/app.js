import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Home from './Home';
import About from './About';
import Contact from './Contact';
import NoMatch from './NoMatch';
import Project from "./Project";
import Layout from "./components/Layout";
import NavigationBar from "./components/NavegationBar";
import Jumbotron from "./components/Jumbotron";
import logo from './logo.svg';
import './app.css';
import MainPage from "./main-page.js";



class App extends Component {

  renderGithubHome = () => <MainPage/>
  
  render() {
    console.log(this.props);
    return (
      <React.Fragment>
      <Router>
        <NavigationBar />
        <Jumbotron />
        <Layout>
            <Switch>
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
              <Route exact path="/" component={Home} />
              <Route exact path="/project" component={Project} />
              <Route path="/about" component={About} />
              <Route path="/contact" component={Contact} />
              <Route path="/github" render={this.renderGithubHome} />
              <Route component={NoMatch} />
            </Switch>
        </Layout>
      </Router>
    </React.Fragment>      
    );
  }
}

export default App;
