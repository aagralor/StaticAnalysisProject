import { FETCH_CUSTOMERS } from "./../constants";
import { createAction } from "redux-actions";
import { apiGet } from "./../apis";
import { urlCustomers } from "./../apis/urls";


export const fetchCustomers= createAction(FETCH_CUSTOMERS, apiGet(urlCustomers));