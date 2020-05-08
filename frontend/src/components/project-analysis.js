import React, { Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { Card, Nav, Accordion, Row, Col } from 'react-bootstrap';
import parse from 'html-react-parser';
import { storeAnalysis } from "../actions/store-analysis";
import { urlAnalysisProject } from "../apis/urls";
import { apiGet } from "../apis";
import { getAnalysis } from '../selectors/github';



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
    const renderList = (!(this.props.analysis && this.props.analysis.sast) ? [] : this.props.analysis.sast.issueList);
    // renderList.forEach(element => {
    //   console.log(JSON.stringify(element));
    // });    
    debugger;
    return (      
    <div>
        <h2>Issue List</h2>
        <p>Meow meow, i tell my human purr for no reason but chase after silly colored fish toys around the house thinking longingly about tuna brine hack, but where is my slave? I'm getting hungry. Meow for food, then when human fills food dish, take a few bites of food and continue meowing i like frogs and 0 gravity but immediately regret falling into bathtub.</p>
          <Accordion defaultActiveKey="0">
            { renderList &&
              renderList.map(element => {
                const priorityColor = (element.priority === 'High' ? 'red' : (element.priority === 'Medium' ? 'orange' : 'green'));
                return (
                  <Card>
                    <Accordion.Toggle as={Card.Header} eventKey={`${element.abbrev}-${element.filename}-${element.lineNumber}`} >
                      <Row>
                        <Col>                      
                          <Card.Title><b>{element.name}</b></Card.Title>
                        </Col>
                        <Col xs lg="2">
                          <Nav variant="pills" defaultActiveKey="#active" >
                            <Nav.Item>
                              <Nav.Link style={{ 'background': priorityColor, 'width': '120px', 'text-align': 'center' }} href="#active">{element.priority}</Nav.Link>
                            </Nav.Item>
                          </Nav>
                        </Col>
                      </Row>
                      <Row>
                        <Col md={{ span: 6, offset: 0 }}>Class: {element.className}</Col>
                        <Col md={{ span: 3, offset: 1 }}>Method: {element.methodName}</Col>
                      </Row>
                      <Row>
                        <Col md={{ span: 6, offset: 0 }}>File: {element.fileName}</Col>
                        <Col md={{ span: 3, offset: 1 }}>Line: {element.lineNumber}</Col>
                      </Row>
                      <Row style={{ 'margin-top': '50px' }} >
                        <Col>
                          <Card.Subtitle><div dangerouslySetInnerHTML={{ __html: this.htmlDecode(element.message) }} /></Card.Subtitle>
                        </Col>
                      </Row>
                    </Accordion.Toggle>
                    <Accordion.Collapse eventKey={`${element.abbrev}-${element.filename}-${element.lineNumber}`} >
                      <Card.Body>
                        {parse(element.warningHtml)}
                        {/* <Card.Title>{element.warningHtml}</Card.Title> */}
                        {/* <Card.Text><div dangerouslySetInnerHTML={{ __html: this.htmlDecode(element.warningHtml) }} /></Card.Text> */}
                      </Card.Body>
                    </Accordion.Collapse>
                  </Card>
                )
              }
            )}
          </Accordion>
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
  analysis: PropTypes.object,
  setAnalysis: PropTypes.func,
}
  
const mapStateToPropsActions = state => ({
  analysis: getAnalysis(state),
});
  
const mapDispatchToPropsActions = dispatch => ({
  setAnalysis: analysis => dispatch(storeAnalysis(analysis)),
});
  
export default withRouter(connect(mapStateToPropsActions, mapDispatchToPropsActions)(ProjectAnalysis));
  