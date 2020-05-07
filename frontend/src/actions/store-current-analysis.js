import { createAction } from "redux-actions";
import { STORE_CURRENT_ANALYSIS } from "../constants/github";


export const storeCurrentAnalysis = createAction(
    STORE_CURRENT_ANALYSIS,
    currentAnalysis => currentAnalysis,
);