import { createAction } from "redux-actions";
import { STORE_ANALYSIS } from "../constants/github";


export const storeAnalysis = createAction(STORE_ANALYSIS, currentAnalysis => currentAnalysis);