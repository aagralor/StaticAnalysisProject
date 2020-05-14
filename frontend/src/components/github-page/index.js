import React, { Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import {
  Card,
  Button,
} from 'react-bootstrap';
import { getToken, getInstallation } from "../../selectors/github"
import { storeInstallation } from "../../actions/store-installation";
import { storeToken } from "../../actions/store-token";
import { finishStoreToken } from "../../actions/finish-store-token";
import {
  urlAccessToken,
  urlCurrentInstallation,
  urlAddBearerTokenToProject,
} from "../../apis/urls";
import { apiGet, apiPost } from "../../apis";
import logo from '../../logo.svg';
import './github-page.css';
import { Alert } from 'react-bootstrap';

class GithubPage extends Component {

  componentWillMount() {
    const params = new URLSearchParams(this.props.location.search);
    const inst = apiGet(urlCurrentInstallation(params.get('installation_id')));
    setTimeout(() => this.props.setInstallation(inst), 500);
  }

  componentWillReceiveProps(nextProps) {
    const body = (nextProps.token && nextProps.token.body);
    if (body && !body.error) {
      const bearerToken = body.access_token;
      const installationId = nextProps.installation.installationId;
      const repositoryId = nextProps.installation.repositoryId;
      const repositoryName = nextProps.installation.repositoryName;
      const user = nextProps.installation.user;
      const newProject = apiPost(urlAddBearerTokenToProject, {
        user,
        installationId,
        repositoryId,
        repositoryName,
        bearerToken
      });
      setTimeout(() => console.log(newProject), 1500);
      this.props.finishTokenProcess();
      this.props.history.push('/project');
    }
  }

  handleClick(code) {
    const requestOptions = {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify({ code }),
    };
    const token = apiPost(urlAccessToken, { code });
    setTimeout(() => this.props.setToken(token), 500);
  }

  render() {
    const params = new URLSearchParams(this.props.location.search);

    const code = params.get('code');
    const instId = params.get('installation_id');
    const action = params.get('setup_action');

    const renderForToken = (this.props.installation && code &&
        this.props.installation.installationId === Number(instId));
    return (
      <div>
        {
          renderForToken &&
          <Card className="text-center">
            <Card.Header>Featured</Card.Header>
            <Card.Body>
              <Card.Title>Special title treatment</Card.Title>
              <Card.Text>

                With supporting text below as a natural lead-in to additional content.
              </Card.Text>
              <Button onClick={() => this.handleClick(code)} variant="primary">Go somewhere</Button>
            </Card.Body>
            <Card.Footer className="text-muted">2 days ago</Card.Footer>
          </Card>
        }
      </div>
    );
  }
}

GithubPage.propTypes = {
  match: PropTypes.object,
  history: PropTypes.object,
  location: PropTypes.object,
  search: PropTypes.object,
  installation: PropTypes.string,
  setInstallation: PropTypes.func,
  finishTokenProcess: PropTypes.func,
}

const mapStateToPropsActions = state => ({
  installation: getInstallation(state),
  token: getToken(state),
});

const mapDispatchToPropsActions = dispatch => ({
  setInstallation: installation => dispatch(storeInstallation(installation)),
  setToken: token => dispatch(storeToken(token)),
  finishTokenProcess: () => dispatch(finishStoreToken()),
});

export default withRouter(connect(mapStateToPropsActions, mapDispatchToPropsActions)(GithubPage));
