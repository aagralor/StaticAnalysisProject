// import { createSelector } from "reselect";

export const getCode = state => state.github.code;

export const getInstallation = state => state.github.installation;

export const getToken = state => state.github.token;

export const getProjectList = state => state.github.projectList;

export const getAnalysis = state => state.github.analysis;

export const getCurrentAnalysis = state => state.github.currentAnalysis;

export const getCurrentAnalysisKey = state => state.github.currentAnalysisKey;

export const getCurrentAnalysisCompletion = state => state.github.currentAnalysisCompletion;

export const getCurrentAnalysisCount = state => state.github.currentAnalysisCount;

export const getSuppressionList = state => state.github.suppressionList;

// export const getCostumerByDni = createSelector(
//   (state, props) => state.customers.find(c => c.dni === props.dni),
//   customer => customer
// )

