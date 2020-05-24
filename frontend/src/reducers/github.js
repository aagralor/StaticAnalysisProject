import { /* handleAction,  */handleActions } from "redux-actions";
import { 
  STORE_CODE, 
  STORE_PROJECT, 
  STORE_PROJECT_LIST, 
  STORE_ANALYSIS, 
  STORE_CURRENT_ANALYSIS, 
  STORE_CURRENT_ANALYSIS_KEY,
  STORE_CURRENT_ANALYSIS_COMPLETION,
  FINISH_ANALYSIS_SAST,
  STORE_INSTALLATION,
  STORE_TOKEN,
  FINISH_STORE_TOKEN,
  STORE_SUPPRESSION_LIST,
} from "../constants/github";

// const customers = handleAction(FETCH_CUSTOMERS, state => state);

export const github = handleActions(
  {
    [STORE_CODE]: (state, action) => ({ ...state, code: action.payload }),
    [STORE_PROJECT]: (state, action) => ({ ...state, project: action.payload }),
    [STORE_PROJECT_LIST]: (state, action) => ({ ...state, projectList: action.payload }),
    [STORE_ANALYSIS]: (state, action) => ({ ...state, analysis: action.payload }),
    [STORE_CURRENT_ANALYSIS]: (state, action) => ({ ...state, currentAnalysis: action.payload }),
    [STORE_CURRENT_ANALYSIS_KEY]: (state, action) => ({ 
      ...state,
      currentAnalysisKey: action.payload,
      currentAnalysis: {
        completion: "0",
        status: "RUNNING"
      }
    }),
    [STORE_CURRENT_ANALYSIS_COMPLETION]: (state, action) => {
      const count = state.currentAnalysisCount;
      const stateCount = typeof(count) !== 'undefined';
      const newCount = (stateCount ? state.currentAnalysisCount + 1 : 0);
      return ({
        ...state,
        currentAnalysisCompletion: action.payload,
        currentAnalysisCount: newCount,
      })
    },
    [FINISH_ANALYSIS_SAST]: (state, action) => ({ 
      ...state,
      currentAnalysisKey: "",
      currentAnalysis: {},
      currentAnalysisCompletion: {},
      currentAnalysisCount: 0,
    }),
    [STORE_INSTALLATION]: (state, action) => ({ 
      ...state,
      installation: action.payload,
    }),
    [STORE_TOKEN]: (state, action) => ({ 
      ...state,
      token: action.payload,
    }),
    [FINISH_STORE_TOKEN]: (state) => ({ 
      ...state,
      token: {},
      installation: {},
    }),
    [STORE_SUPPRESSION_LIST]: (state, action) => ({ ...state, suppressionList: action.payload }),
  }, 
  {}
);

