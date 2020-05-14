import React, { Component } from 'react';
import PropTypes from "prop-types";
import CustomProgressBar from "./custom-progress-bar";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { Card, Nav, Button, Modal } from 'react-bootstrap';
import { storeProjectList } from "../../actions/store-project-list";
import { storeCurrentAnalysis } from "../../actions/store-current-analysis";
import { storeCurrentAnalysisKey } from "../../actions/store-current-analysis-key";
import { storeCurrentAnalysisCompletion } from "../../actions/store-current-analysis-completion";
import { finishAnalysisSast } from "../../actions/finish-analysis-sast";
import { urlProject, urlStartAnalysis, urlCurrentAnalysisStatus } from "../../apis/urls";
import { modalExample } from "./modal-example";
import { apiGet } from "../../apis";
import {
  getProjectList,
  getCurrentAnalysis,
  getCurrentAnalysisKey,
  getCurrentAnalysisCompletion,
  // getCurrentAnalysisCount
} from '../../selectors/github';



class Project extends Component {

  componentWillMount() {
    const projectList = apiGet(urlProject);
    setTimeout(() => this.props.setProjectList(projectList), 500);
  }

  handleClick(projectKey) {
    const startedAnalysis = apiGet(urlStartAnalysis(projectKey));
    this.props.setCurrentAnalysisKey(projectKey);
    setTimeout(() => {
      this.props.setCurrentAnalysis(startedAnalysis);
    }, 500);
  }

  checkProgress = null;

  render() {
    const renderList = (!this.props.projectList ? [] : this.props.projectList);
    if (this.props.currentAnalysis && this.props.currentAnalysis.id
        && this.props.currentAnalysis.status === 'RUNNING') {

      if (this.checkProgress === null) {
        this.checkProgress = setInterval(() => {
          const currentCompletion = apiGet(urlCurrentAnalysisStatus(this.props.currentAnalysis.id));
          setTimeout(() => {
            this.props.setAnalysisCompletion(currentCompletion);
          } , 500);
        } , 1500);
      }
    
    }

    if (this.checkProgress !== null && this.props.analysisCompletion &&
        this.props.analysisCompletion.status  && 
        this.props.analysisCompletion.status !== 'RUNNING') {
      clearInterval(this.checkProgress);
      this.checkProgress = null;
      this.props.finishAnalysis();
    } 


    return (      
      <div>
        <h2>Project List</h2>
        <p>Meow meow, i tell my human purr for no reason but chase after silly colored fish toys around the house thinking longingly about tuna brine hack, but where is my slave? I'm getting hungry. Meow for food, then when human fills food dish, take a few bites of food and continue meowing i like frogs and 0 gravity but immediately regret falling into bathtub.</p>
        { 
          renderList.map(element =>
            <Card>
              <Card.Header>
                <Nav variant="pills" defaultActiveKey="#enabled" >
                  <Nav.Item>
                    <Nav.Link href="#enabled">Enabled</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href="#disabled" disabled>Disabled</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href={`/project/${element.key}`}>Access</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href={`#edit?key=${element.key}`}>Edit</Nav.Link>
                  </Nav.Item>
                  <Nav.Item >
                    <Nav.Link href="#remove">Remove</Nav.Link>
                  </Nav.Item>
                </Nav>
              </Card.Header>
              <Card.Body>
                <Card.Title>{element.name}</Card.Title>
                <Card.Text>
                  With supporting text below as a natural lead-in to additional content.
                </Card.Text>
                {
                  element.isPrivate && !element.bearerToken && 
                  <Button onClick={event =>  window.location.href='https://github.com/apps/issuesecuritycenter'} variant="primary" style={{ 'margin-right': '5px' }}>Integrate repository</Button>
                }
                <Button onClick={event =>  window.location.href=element.url} variant="primary" style={{ 'margin-right': '5px' }}>Visit Github</Button>
                <Button onClick={() => this.handleClick(element.key)} variant="primary">Start Analysis</Button>
              </Card.Body>
              {
                !!this.props.currentAnalysis &&
                !!this.props.currentAnalysisKey &&
                this.props.currentAnalysisKey === element.key &&
                this.props.analysisCompletion &&
                this.props.analysisCompletion.value &&
                <Card.Footer className="text-muted">
                  <CustomProgressBar analysisCompletion={this.props.analysisCompletion.value} />
                </Card.Footer>
              }
            </Card>
          )
        }
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
  currentAnalysis: PropTypes.object,
  setProjectList: PropTypes.func,
  setCurrentAnalysis: PropTypes.func,
  currentAnalysisKey: PropTypes.string,
  setAnalysisCompletion: PropTypes.func,
  finishAnalysis: PropTypes.func,
}
  
const mapStateToPropsActions = state => ({
  projectList: getProjectList(state),
  currentAnalysis: getCurrentAnalysis(state),
  currentAnalysisKey: getCurrentAnalysisKey(state),
  analysisCompletion: getCurrentAnalysisCompletion(state),
});
  
const mapDispatchToPropsActions = dispatch => ({
  setProjectList: projectList => dispatch(storeProjectList(projectList)),
  setCurrentAnalysis: currentAnalysis => dispatch(storeCurrentAnalysis(currentAnalysis)),
  setCurrentAnalysisKey: projectKey => dispatch(storeCurrentAnalysisKey(projectKey)),
  setAnalysisCompletion: completion => dispatch(storeCurrentAnalysisCompletion(completion)),
  finishAnalysis: () => dispatch(finishAnalysisSast()),
});
  
export default withRouter(connect(mapStateToPropsActions, mapDispatchToPropsActions)(Project));
  