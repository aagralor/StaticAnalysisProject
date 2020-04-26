import { createAction } from "redux-actions";
import { STORE_PROJECT_LIST } from "../constants/github";


export const storeProjectList = createAction(STORE_PROJECT_LIST, projectList => projectList);