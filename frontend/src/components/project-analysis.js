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
    const renderSastList = (!(this.props.analysis && this.props.analysis.sast) ? [] : this.props.analysis.sast.issueList);
    const renderScaList = (!(this.props.analysis && this.props.analysis.sca) ? [] : this.props.analysis.sca.dependencyList);
    // renderList.forEach(element => {
    //   console.log(JSON.stringify(element));
    // });    
    // debugger;
    return (      
    <div>
        <h2>Issue List</h2>
        <p>Meow meow, i tell my human purr for no reason but chase after silly colored fish toys around the house thinking longingly about tuna brine hack, but where is my slave? I'm getting hungry. Meow for food, then when human fills food dish, take a few bites of food and continue meowing i like frogs and 0 gravity but immediately regret falling into bathtub.</p>
        <Card>
          <Card.Header>
            <Nav variant="tabs" defaultActiveKey="#SAST">
              <Nav.Item>
                <Nav.Link href="#SAST">SAST</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link href="#SCA">SCA</Nav.Link>
              </Nav.Item>
            </Nav>
          </Card.Header>
          <Card.Body>
          
          <Accordion defaultActiveKey="0">
            { 
              this.props.location.hash !== '#SCA' &&
              renderSastList &&
              renderSastList.map(element => {
                const priorityColor = (element.priority === 'High' ? 'red' : (element.priority === 'Medium' ? 'orange' : 'green'));
                return (
                  <Card style={{ 'margin-top': '20px' }}>
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
              })
            }
            { 
              this.props.location.hash === '#SCA' &&
              renderScaList &&
              renderScaList
              .filter(element => (element.vulnerabilityList && element.vulnerabilityList.length > 0))
              .map(element => {
                // debugger;
                return (
                  <Card style={{ 'margin-top': '20px' }}>
                    <Accordion.Toggle as={Card.Header} eventKey={element.sha256} >
                      <Row>
                        <Col>                      
                          <Card.Title><b>3rd party library: {element.fileName}</b></Card.Title>
                        </Col>
                        <Col xs lg="2">
                          <Nav variant="pills" defaultActiveKey="#active" >
                            <Nav.Item>
                              <Nav.Link
                                style={{
                                  'background': 'blue',
                                  'width': '120px',
                                  'text-align': 'center'
                                }} href="#active"
                              >
                                {element.vulnerabilityList.length}
                              </Nav.Link>
                            </Nav.Item>
                          </Nav>
                        </Col>
                      </Row>
                      {/* <Row style={{ 'margin-top': '25px' }} >
                        <Col>
                          <Card.Subtitle>{element.filePath}</Card.Subtitle>
                        </Col>
                      </Row> */}
                    </Accordion.Toggle>
                    <Accordion.Collapse eventKey={element.sha256} >
                      <Card.Body>
                        {/* <Card.Title>{element.warningHtml}</Card.Title> */}
                        {/* <Card.Text><div dangerouslySetInnerHTML={{ __html: this.htmlDecode(element.warningHtml) }} /></Card.Text> */}
                        <Accordion>

                          {element.vulnerabilityList.map(vuln => {
                            const priorityColor = (vuln.severity === 'CRITICAL' ? 'red' : (
                              vuln.severity === 'HIGH' ? 'orange' : (
                                vuln.severity === 'MEDIUM' ? 'yellow' : 'green')));
                            return(
                            <Card style={{ 'margin-top': '20px' }}>
                              
                              <Accordion.Toggle as={Card.Header} eventKey={`${element.sha256}-${vuln.name}`} >
                                <Row>
                                  <Col>                      
                                    <Card.Title><b>{vuln.name}</b></Card.Title>
                                  </Col>
                                  <Col xs lg="2">
                                    <Nav variant="pills" defaultActiveKey="#active" >
                                      <Nav.Item>
                                        <Nav.Link style={{ 'background': priorityColor, 'width': '120px', 'text-align': 'center', 'color': '#000' }} href="#active">{vuln.severity}</Nav.Link>
                                      </Nav.Item>
                                    </Nav>
                                  </Col>
                                </Row>
                                <Row style={{ 'margin-top': '20px' }} >
                                  <Col md={{ span: 6, offset: 0 }}><b>CWE: </b>{vuln.cweList[0]}</Col>
                                  <Col md={{ span: 3, offset: 1 }}><b>Source: </b>{vuln.source}</Col>
                                </Row>
                                <Row style={{ 'margin-top': '30px' }} >
                                  <Col>
                                    <Card.Subtitle><div dangerouslySetInnerHTML={{ __html: this.htmlDecode(vuln.description) }} /></Card.Subtitle>
                                  </Col>
                                </Row>
                              </Accordion.Toggle>
                              <Accordion.Collapse eventKey={`${element.sha256}-${vuln.name}`} >
                                <Card.Body>
                                  <Row>
                                    <Col>
                                      <Card>
                                        <Card.Header><b>CVSS-v2</b></Card.Header>
                                        <Card.Body>
                                          {
                                            vuln.cvssv2 &&
                                            <ul>
                                            <li><b>Severity: </b>{vuln.cvssv2.severity}</li>
                                            <li><b>Score: </b>{vuln.cvssv2.score}</li>
                                            <li><b>Attack complexity: </b>{vuln.cvssv2.accessComplexity}</li>
                                            <li><b>Attack vector: </b>{vuln.cvssv2.accessVector}</li>
                                            <li><b>Availability Impact: </b>{vuln.cvssv2.availabilityImpact}</li>
                                            <li><b>Confidential Impact: </b>{vuln.cvssv2.confidentialImpact}</li>
                                            <li><b>Integrity Impact: </b>{vuln.cvssv2.integrityImpact}</li>
                                            </ul>
                                          }
                                        </Card.Body>
                                      </Card>
                                    </Col>
                                    <Col>
                                      <Card>
                                        <Card.Header><b>CVSS-v3</b></Card.Header>
                                        <Card.Body>
                                        {
                                            vuln.cvssv3 &&
                                            <ul>
                                            <li><b>Severity: </b>{vuln.cvssv3.baseSeverity}</li>
                                            <li><b>Score: </b>{vuln.cvssv3.baseScore}</li>
                                            <li><b>Attack complexity: </b>{vuln.cvssv3.attackComplexity}</li>
                                            <li><b>Attack vector: </b>{vuln.cvssv3.attackVector}</li>
                                            <li><b>Availability Impact: </b>{vuln.cvssv3.availabilityImpact}</li>
                                            <li><b>Confidential Impact: </b>{vuln.cvssv3.confidentialityImpact}</li>
                                            <li><b>Integrity Impact: </b>{vuln.cvssv3.integrityImpact}</li>
                                            </ul>
                                          }
                                        </Card.Body>
                                      </Card>
                                    </Col>                                    
                                  </Row>
                                  <Row style={{ 'margin-top': '20px' }} className="justify-content-md-center" >
                                    <Card>
                                      <Card.Header><b>References</b></Card.Header>
                                      <Card.Body>
                                        {
                                          vuln.referenceList &&
                                          vuln.referenceList.map(r => (
                                            <p>
                                              <Card.Link href={r.url}>
                                                {`${r.source}: ${r.url}`}
                                              </Card.Link>
                                            </p>
                                          ))
                                        }
                                      </Card.Body>
                                    </Card>
                                  </Row>
                                </Card.Body>
                              </Accordion.Collapse>
                              
                            </Card>

                          )})}
                        </Accordion>
                      </Card.Body>
                    </Accordion.Collapse>
                  </Card>
                )
              })
            }
          </Accordion>

          </Card.Body>
        </Card>
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
  