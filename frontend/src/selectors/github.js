// import { createSelector } from "reselect";

export const getCode = state => state.github.code;

export const getProjectList = state => state.github.projectList;

export const getCurrentAnalysis = state => state.github.currentAnalysis;

// export const getCostumerByDni = createSelector(
//   (state, props) => state.customers.find(c => c.dni === props.dni),
//   customer => customer
// )

