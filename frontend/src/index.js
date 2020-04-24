import React from 'react';
import ReactDOM from 'react-dom';
import App from './app';
import registerServiceWorker from './registerServiceWorker';
import { Provider } from "react-redux";
import { store } from "./store";
import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';


const rootComponent = (
    <React.StrictMode>
        <Provider store={store}>
            <App/>
        </Provider>
    </React.StrictMode>
)

ReactDOM.render(rootComponent, document.getElementById('root'));
registerServiceWorker();
