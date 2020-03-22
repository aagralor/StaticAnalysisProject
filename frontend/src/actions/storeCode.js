import { createAction } from "redux-actions";
import { STORE_CODE } from "../constants/github";


export const storeCode = createAction(STORE_CODE, code => code);