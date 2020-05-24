import React, { Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { Form } from 'react-bootstrap';
import CustomForm from './utils/custom-form';
import { apiPost } from "../apis";
import { urlSuppression } from "../apis/urls";
import { storeSuppressionList } from "../actions/store-suppression-list";


class CreateSuppression extends Component {

  isRegex = false;

  render() {
    return (      
    <div>
        <h2>Create Suppression</h2>
        <br/><br/>
        <CustomForm 
          submit={data => { 
              const notes=data.get('notes');
              const cve=data.get('cve');
              const filePath=data.get('filePath');
              const filePathIsRegex=(data.get('checkBoxData') === 'on');
              const sha1=data.get('sha1');

              let suppress = { notes, cve };
              if (filePath) {
                suppress = { ...suppress, filePath, filePathIsRegex }
              }
              if (sha1) {
                suppress = { ...suppress, sha1 }
              }

              const newSuppressions = apiPost(urlSuppression, { notes, cve, filePath, filePathIsRegex, sha1 });
              setTimeout(() => this.props.setSuppressionList(newSuppressions), 0);
              this.props.history.push('/suppress');
          }}
          submitButtonText={'Accept'}
        >
          <Form.Group controlId="formBasicText1">
            <Form.Label>Notes</Form.Label>
            <Form.Control type="text" placeholder="Enter an the reason of this suppression" name="notes" />
          </Form.Group>

          <Form.Group controlId="formBasicText2">
            <Form.Label>CVE</Form.Label>
            <Form.Control type="text" placeholder="Enter a CVE key (5 to 30)" name="cve" />
          </Form.Group>          

          <Form.Group controlId="formBasicText3">
            <Form.Label>File Path</Form.Label>
            <Form.Control type="text" placeholder="Enter file path" name="filePath" />
          </Form.Group>

          <Form.Group controlId="formBasicCheckbox" onChange={(x, y) => { console.log('change'); console.log(x); this.isRegex = !this.isRegex; }}>
            <Form.Check type="checkbox" label="Private Repository" name="checkBoxData" />
          </Form.Group>

          <Form.Group controlId="formBasicText4">
            <Form.Label>SHA1</Form.Label>
            <Form.Control type="text" placeholder="Enter SHA1 hash" name="sha1" />
          </Form.Group>
        </CustomForm>   
    </div>
    )
  }
}

CreateSuppression.propTypes = {
  match: PropTypes.object,
  history: PropTypes.object,
  location: PropTypes.object,
  search: PropTypes.object,
  setSuppressionList: PropTypes.func,
}
  
const mapDispatchToPropsActions = dispatch => ({
  setSuppressionList: suppressionList => dispatch(storeSuppressionList(suppressionList)),
});
  
export default withRouter(connect(null, mapDispatchToPropsActions)(CreateSuppression));
  