import React, { Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { ProgressBar } from 'react-bootstrap';




class CustomProgressBar extends Component {

  componentWillMount() {
  }
  
  render() {  
    return (  
      <ProgressBar animated now={Number(this.props.analysisCompletion)} />
    )
  }
}

CustomProgressBar.propTypes = {
  analysisCompletion: PropTypes.string,
}
  
const mapStateToPropsActions = state => ({
  // projectList: getProjectList(state),
  // currentAnalysis: getCurrentAnalysis(state),
  // currentAnalysisKey: getCurrentAnalysisKey(state),
  // analysisCompletion: getCurrentAnalysisCompletion(state),
  // currentAnalysisCount: getCurrentAnalysisCount(state),
});
  
const mapDispatchToPropsActions = dispatch => ({
  // setProjectList: projectList => dispatch(storeProjectList(projectList)),
  // setCurrentAnalysis: currentAnalysis => dispatch(storeCurrentAnalysis(currentAnalysis)),
  // setCurrentAnalysisKey: projectKey => dispatch(storeCurrentAnalysisKey(projectKey)),
  // setAnalysisCompletion: completion => dispatch(storeCurrentAnalysisCompletion(completion)),
});
  
export default withRouter(connect(mapStateToPropsActions, mapDispatchToPropsActions)(CustomProgressBar));
  