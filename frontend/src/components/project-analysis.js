import React, { Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { Card, Nav, Button } from 'react-bootstrap';
import { storeAnalysis } from "../actions/store-analysis";
import { urlAnalysisProject } from "../apis/urls";
import { apiGet } from "../apis";
import { getCurrentAnalysis } from '../selectors/github';



class ProjectAnalysis extends Component {

  componentWillMount() {
    const analysis = apiGet(urlAnalysisProject(this.props.match.params.projectKey));
    setTimeout(() => this.props.setAnalysis(analysis), 500);
  }

  handleClick() {
    this.props.history.push('/');
  }

  htmlDecode(input){
    var e = document.createElement('div');
    e.innerHTML = input;
    return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
  }

  render() {
    const renderList = (!this.props.currentAnalysis ? [] : this.props.currentAnalysis.issueList);
    // renderList.forEach(element => {
    //   console.log(JSON.stringify(element));
    // });    
    debugger;
    return (      
    <div>
        <h2>Issue List</h2>
        <p>Meow meow, i tell my human purr for no reason but chase after silly colored fish toys around the house thinking longingly about tuna brine hack, but where is my slave? I'm getting hungry. Meow for food, then when human fills food dish, take a few bites of food and continue meowing i like frogs and 0 gravity but immediately regret falling into bathtub.</p>
        { renderList &&
          renderList.map(element =>
            <Card>
              <Card.Header>
                <Nav variant="pills" defaultActiveKey="#active" >
                  <Nav.Item>
                    <Nav.Link href="#active">Active</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href="#done">Done</Nav.Link>
                  </Nav.Item>
                  <Nav.Item >
                    <Nav.Link href="#remove">Remove</Nav.Link>
                  </Nav.Item>
                </Nav>
              </Card.Header>
              <Card.Body>
                <Card.Title>{element.name}</Card.Title>
                <Card.Text><div dangerouslySetInnerHTML={{ __html: this.htmlDecode(element.message) }} /></Card.Text>
              </Card.Body>
            </Card>
          )}
    </div>
    )
  }
}

ProjectAnalysis.propTypes = {
  match: PropTypes.shape({
    path: PropTypes.string,
    url: PropTypes.string,
    params: PropTypes.shape({
      projectKey: PropTypes.string,
    }),
  }),
  history: PropTypes.object,
  location: PropTypes.object,
  search: PropTypes.object,
  currentAnalysis: PropTypes.string,
  setAnalysis: PropTypes.func,
}
  
const mapStateToPropsActions = state => ({
  currentAnalysis: getCurrentAnalysis(state),
});
  
const mapDispatchToPropsActions = dispatch => ({
  setAnalysis: analysis => dispatch(storeAnalysis(analysis)),
});
  
export default withRouter(connect(mapStateToPropsActions, mapDispatchToPropsActions)(ProjectAnalysis));
  