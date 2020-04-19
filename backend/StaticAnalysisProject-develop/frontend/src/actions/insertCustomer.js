import { createAction } from "redux-actions";
import { INSERT_CUSTOMER } from "../constants";
import { apiPost } from "../apis";
import { urlCustomers } from "../apis/urls";


export const insertCustomer = createAction(INSERT_CUSTOMER, 
  (customer) => apiPost(urlCustomers, customer)()
);