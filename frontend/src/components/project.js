import React, { Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { Card, Nav, Button } from 'react-bootstrap';
import { storeProjectList } from "../actions/store-project-list";
import { urlProject } from "../apis/urls";
import { apiGet } from "../apis";
import { getProjectList } from '../selectors/github';



class Project extends Component {

  componentWillMount() {
    const projectList = apiGet(urlProject);
    setTimeout(() => this.props.setProjectList(projectList), 500);
  }

  render() {
    debugger;
    const renderList = (!this.props.projectList ? [] : this.props.projectList);
    renderList.forEach(element => {
      console.log(JSON.stringify(element));
    });    
    return (      
    <div>
        <h2>Project List</h2>
        <p>Meow meow, i tell my human purr for no reason but chase after silly colored fish toys around the house thinking longingly about tuna brine hack, but where is my slave? I'm getting hungry. Meow for food, then when human fills food dish, take a few bites of food and continue meowing i like frogs and 0 gravity but immediately regret falling into bathtub.</p>
        { 
          renderList.map(element =>
            <Card>
              <Card.Header>
                <Nav variant="pills" defaultActiveKey="#first">
                  <Nav.Item>
                    <Nav.Link href="#first">Access</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href="https://github.com">Link</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href={`/project/${element.key}`}>Edit</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href="#disabled" disabled>
                      Disabled
                    </Nav.Link>
                  </Nav.Item>
                </Nav>
              </Card.Header>
              <Card.Body>
                <Card.Title>{element.name}</Card.Title>
                <Card.Text>
                  With supporting text below as a natural lead-in to additional content.
                </Card.Text>
                <Button variant="primary">Visit Github</Button>
              </Card.Body>
            </Card>
          )}
    </div>
    )
  }
}

Project.propTypes = {
  match: PropTypes.object,
  history: PropTypes.object,
  location: PropTypes.object,
  search: PropTypes.object,
  projectList: PropTypes.string,
  setProjectList: PropTypes.func,
}
  
const mapStateToPropsActions = state => ({
  projectList: getProjectList(state),
});
  
const mapDispatchToPropsActions = dispatch => ({
  setProjectList: projectList => dispatch(storeProjectList(projectList)),
});
  
export default withRouter(connect(mapStateToPropsActions, mapDispatchToPropsActions)(Project));
  