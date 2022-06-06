import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {applyMiddleware, combineReducers, compose, createStore} from "redux";
import user from './modules/user'
import product from './modules/product'
import {Provider} from "react-redux";
import 'bootstrap/dist/css/bootstrap.min.css'

const handleAsync = storeAPI => next => action =>{
    if (typeof action === 'function'){
        return action(storeAPI.dispatch, storeAPI.getState)
    }
    next(action)
}

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(
    combineReducers({user, product}),
    composeEnhancers(applyMiddleware(handleAsync)))




const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Provider store={store}>
        <React.StrictMode>
            <App />
        </React.StrictMode>
    </Provider>

);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
