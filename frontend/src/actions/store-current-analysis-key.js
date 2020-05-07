import { createAction } from "redux-actions";
import { STORE_CURRENT_ANALYSIS_KEY } from "../constants/github";


export const storeCurrentAnalysisKey = createAction(
    STORE_CURRENT_ANALYSIS_KEY,
    currentAnalysisKey => currentAnalysisKey,
);