import { createAction } from "redux-actions";
import { STORE_TOKEN } from "../constants/github";


export const storeToken = createAction(STORE_TOKEN, token => token);