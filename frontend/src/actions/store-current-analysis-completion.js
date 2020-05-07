import { createAction } from "redux-actions";
import { STORE_CURRENT_ANALYSIS_COMPLETION } from "../constants/github";


export const storeCurrentAnalysisCompletion = createAction(
    STORE_CURRENT_ANALYSIS_COMPLETION,
    completion => completion,
);