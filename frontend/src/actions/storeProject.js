import { createAction } from "redux-actions";
import { STORE_PROJECT } from "../constants/github";


export const storeProject = createAction(STORE_PROJECT, project => project);