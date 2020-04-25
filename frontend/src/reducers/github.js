import { /* handleAction,  */handleActions } from "redux-actions";
// import { FETCH_CUSTOMERS, INSERT_CUSTOMER, UPDATE_CUSTOMER, DELETE_CUSTOMER } from "../constants";
import { STORE_CODE, STORE_PROJECT } from "../constants/github";

// const customers = handleAction(FETCH_CUSTOMERS, state => state);

export const github = handleActions(
  {
    [STORE_CODE]: (state, action) => ({ ...state, code: action.payload }),
    [STORE_PROJECT]: (state, action) => ({ ...state, project: action.payload }),
    // [FETCH_CUSTOMERS]: (state, action) => [...action.payload],
    // [INSERT_CUSTOMER]: (state, action) => [...state, action.payload],
    // [UPDATE_CUSTOMER]: (state, action) => {
    //   const customerPayload = action.payload;
    //   const { id } = customerPayload;
    //   const customers = state;
    //   const initialValue = [];
    //   const newCustomers = customers.reduce( (acc, customer) => {
    //     if (customer.id === id) {
    //       return [...acc, customerPayload];
    //     } else {
    //       return [...acc, customer];
    //     }
    //   }, initialValue);
    //   return newCustomers;
    // },
    // [DELETE_CUSTOMER]: (state, action) => state.filter(c => c.id !== action.payload),
  }, 
  {}
);

