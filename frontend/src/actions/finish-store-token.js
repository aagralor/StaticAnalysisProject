import { createAction } from "redux-actions";
import { FINISH_STORE_TOKEN } from "../constants/github";


export const finishStoreToken = createAction(FINISH_STORE_TOKEN);