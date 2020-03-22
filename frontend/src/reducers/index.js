import { combineReducers } from "redux";
import { reducer as reduxForm} from "redux-form";
import { customers } from "./customers";
import { github } from "./github";
import { CUSTOMER_LIST, CUSTOMER_VIEW } from "../constants/permissions";


const user = (state, action) => (
  {
    permissions: [CUSTOMER_LIST, CUSTOMER_VIEW]
  }
)

export default combineReducers({
  customers,
  user,
  github,
  form: reduxForm,
});