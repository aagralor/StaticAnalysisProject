import React, { Component } from 'react';
import PropTypes from "prop-types";
import { Row, Col } from "react-bootstrap";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { Card, Nav, Button } from 'react-bootstrap';
import { storeSuppressionList } from "../actions/store-suppression-list";
import { urlSuppression } from "../apis/urls";
import { apiGet } from "../apis";
import {
  getSuppressionList,
} from '../selectors/github';



class Suppression extends Component {

  componentWillMount() {
    const suppressionList = apiGet(urlSuppression);
    setTimeout(() => this.props.setSuppressionList(suppressionList), 500);
  }

  render() {
    const renderList = (!this.props.suppressionList ? [] : this.props.suppressionList);

    return (      
      <div>
        <Row><h2>Suppression List for Dependency Check</h2></Row>
        <br/>
        <br/>
        <Button variant="primary" size="lg" block href="suppress/create">
          Create New Suppression
        </Button>
        <br/>
        <br/>
        { 
          renderList.map(element => {
            debugger;
            return (
              <div><Card>
                <Card.Header>
                  <Nav variant="pills" defaultActiveKey="#enabled" >
                    <Nav.Item>
                      <Nav.Link onClick={() => this.handleReportClick(element.key)}>
                        Edit
                      </Nav.Link>
                    </Nav.Item>
                    <Nav.Item >
                      <Nav.Link href="#remove">Remove</Nav.Link>
                    </Nav.Item>
                  </Nav>
                </Card.Header>
                <Card.Body>
                  <Card.Title><b>CVE: </b>{element.cve}</Card.Title>
                  {
                    element.filePath && 
                      <Card.Text>
                        <Row>
                          <Col md={{ span: 6, offset: 0 }}><b>FilePath: </b>{element.filePath}</Col>
                          <Col md={{ span: 3, offset: 1 }}><b>IsRegex: </b>{String(element.filePathIsRegex)}</Col>
                        </Row>
                      </Card.Text>
                  }
                  {
                    element.sha1 && 
                    <Card.Text>
                      <Row>
                        <Col md={{ span: 6, offset: 0 }}><b>SHA1: </b>{element.sha1}</Col>
                      </Row>
                  </Card.Text>                  }
                </Card.Body>
              </Card><br/></div>
            )
          })
        }
      </div>
    )
  }
}

Suppression.propTypes = {
  match: PropTypes.object,
  history: PropTypes.object,
  location: PropTypes.object,
  search: PropTypes.object,
  suppressionList: PropTypes.object,
  setSuppressionList: PropTypes.func,
}
  
const mapStateToPropsActions = state => ({
  suppressionList: getSuppressionList(state),
});
  
const mapDispatchToPropsActions = dispatch => ({
  setSuppressionList: suppressionList => dispatch(storeSuppressionList(suppressionList)),
});
  
export default withRouter(connect(mapStateToPropsActions, mapDispatchToPropsActions)(Suppression));
  