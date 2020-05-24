import { createAction } from "redux-actions";
import { STORE_SUPPRESSION_LIST } from "../constants/github";


export const storeSuppressionList = createAction(STORE_SUPPRESSION_LIST, suppressionList => suppressionList);