import { createAction } from "redux-actions";
import { FINISH_ANALYSIS_SAST } from "../constants/github";


export const finishAnalysisSast = createAction(
    FINISH_ANALYSIS_SAST
);