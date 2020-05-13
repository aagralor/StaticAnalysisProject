import { createAction } from "redux-actions";
import { STORE_INSTALLATION } from "../constants/github";


export const storeInstallation = createAction(STORE_INSTALLATION, installation => installation);