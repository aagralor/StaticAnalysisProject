import React from 'react';
import ReactDOM from 'react-dom';
import App from './app';
import registerServiceWorker from './registerServiceWorker';
import { Provider } from "react-redux";
import { store } from "./store";
import './index.css';

const rootComponent = (
    <Provider store={store}>
        <App/>
    </Provider>
)

ReactDOM.render(rootComponent, document.getElementById('root'));
registerServiceWorker();
