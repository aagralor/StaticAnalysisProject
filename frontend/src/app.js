import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Home from './components/home';
import About from './components/about';
import Contact from './components/contact';
import NoMatch from './components/no-match';
import Project from './components/project';
import CreateProject from './components/create-project';
import ProjectAnalysis from './components/project-analysis';
import Layout from './components/layout';
import NavigationBar from './components/layout/navigation-bar';
import Jumbotron from './components/layout/jumbotron';
import './app.css';
import GithubPage from './components/github-page';



class App extends Component {

 
  render() {
    console.log(this.props);
    return (
      <React.Fragment>
        <Router>
          <NavigationBar />
          <Jumbotron />
          <Layout>
            <Switch>
              <Route exact path="/" component={Home} />
              <Route exact path="/project/:projectKey" component={ProjectAnalysis} />
              <Route exact path="/project/create" component={CreateProject} />
              <Route exact path="/project" component={Project} />
              <Route path="/about" component={About} />
              <Route path="/contact" component={Contact} />
              <Route path="/github" render={GithubPage} />
              <Route component={NoMatch} />
            </Switch>
          </Layout>
        </Router>
      </React.Fragment>      
    );
  }
}

export default App;
