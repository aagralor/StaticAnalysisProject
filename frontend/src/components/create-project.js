import React, { Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { Form } from 'react-bootstrap';
import CustomForm from './utils/custom-form';
import { apiPost } from "../apis";
import { urlProject } from "../apis/urls";
import { storeProject } from "../actions/store-project";


class CreateProject extends Component {

  isPrivate = false;

  render() {
    return (      
    <div>
        <h2>Create Project</h2>
        <br/><br/>
        <CustomForm 
          submit={data => { 
              const key=data.get('projectKey');
              const name=data.get('projectName');
              const accessToken=data.get('githubAccessToken');
              const email=data.get('githubEmail');
              const username=data.get('githubUsername');
              const repositoryName=data.get('githubRepository');
              const branchName=data.get('githubBranch');
              const url=data.get('githubUrl');
              const isPrivate=(data.get('checkBoxData') === 'on');
              const newProject = apiPost(urlProject, { key, name, accessToken, email, username, repositoryName, branchName, url, isPrivate });
              setTimeout(() => this.props.setProject(newProject), 0);
              this.props.history.push('/project');
          }}
          submitButtonText={'Accept'}
        >
          <Form.Group controlId="formBasicText1">
            <Form.Label>Project name</Form.Label>
            <Form.Control type="text" placeholder="Enter a project name" name="projectName" />
          </Form.Group>

          <Form.Group controlId="formBasicText2">
            <Form.Label>Project key</Form.Label>
            <Form.Control type="text" placeholder="Enter a project key (4 to 8 characters)" name="projectKey" />
          </Form.Group>

          <Form.Group controlId="formBasicText3">
            <Form.Label>Github Username</Form.Label>
            <Form.Control type="text" placeholder="Enter username" name="githubUsername" />
          </Form.Group>

          <Form.Group controlId="formBasicEmail">
            <Form.Label>Github Email address</Form.Label>
            <Form.Control type="email" placeholder="Enter email" name="githubEmail" />
            <Form.Text className="text-muted">
              We'll never share your email with anyone else.
            </Form.Text>
          </Form.Group>

          <Form.Group controlId="formBasicText4">
            <Form.Label>Github Repository</Form.Label>
            <Form.Control type="text" placeholder="Enter repository name" name="githubRepository" />
          </Form.Group>

          <Form.Group controlId="formBasicText5">
            <Form.Label>Github Branch</Form.Label>
            <Form.Control type="text" placeholder="Enter branch name" name="githubBranch" />
          </Form.Group>

          <Form.Group controlId="formBasicText6">
            <Form.Label>Github URL</Form.Label>
            <Form.Control type="text" placeholder="Enter URL of your repository" name="githubUrl" />
          </Form.Group>

          <Form.Group controlId="formBasicCheckbox" onChange={(x, y) => { console.log('change'); console.log(x); this.isPrivate = !this.isPrivate; }}>
            <Form.Check type="checkbox" label="Private Repository" name="checkBoxData" />
          </Form.Group>

          <Form.Group controlId="formBasicText7">
            <Form.Label>Github Access Token</Form.Label>
            <Form.Control type="text" placeholder="Enter an access token to your private repository" name="githubAccessToken" />
          </Form.Group>
        </CustomForm>   
    </div>
    )
  }
}

CreateProject.propTypes = {
  match: PropTypes.object,
  history: PropTypes.object,
  location: PropTypes.object,
  search: PropTypes.object,
  project: PropTypes.string,
  setProject: PropTypes.func,
}
  
// const mapStateToPropsActions = state => ({
//   code: getCode(state),
// });
  
const mapDispatchToPropsActions = dispatch => ({
  setProject: code => dispatch(storeProject(code)),
});
  
export default withRouter(connect(null, mapDispatchToPropsActions)(CreateProject));
  