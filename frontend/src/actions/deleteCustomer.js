import { createAction } from "redux-actions";
import { DELETE_CUSTOMER } from "../constants";
import { apiDelete } from "../apis";
import { urlCustomers } from "../apis/urls";


export const deleteCustomer = createAction(DELETE_CUSTOMER, 
  id => apiDelete(urlCustomers, id)()
);