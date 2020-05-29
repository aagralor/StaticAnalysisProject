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
import { urlProject, urlStartAnalysis, urlCurrentAnalysisStatus, urlGenerateReport } from "../../apis/urls";
import { modalExample } from "./modal-example";
import { apiGet, apiGetPDF } from "../../apis";
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

  handleReportClick(projectKey) {
    const link = document.createElement('a');
    link.href = urlGenerateReport(projectKey);
    link.setAttribute('download', 'app_report.pdf');
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link); 
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
        <br/>
        <br/>
        { 
          renderList.map(element =>
            <div><Card>
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
                    <Nav.Link onClick={() => this.handleReportClick(element.key)}>
                      PDF
                    </Nav.Link>
                  </Nav.Item>
                  <Nav.Item >
                    <Nav.Link href="#remove">Remove</Nav.Link>
                  </Nav.Item>
                </Nav>
              </Card.Header>
              <Card.Body>
                <Card.Text>
                  Project container.
                </Card.Text>
                <Card.Title><h2>{element.name}</h2></Card.Title>
                <br/>
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
            </Card><br/></div>
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
  