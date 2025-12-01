import React from 'react';
import {BrowserRouter, Switch, Route, Redirect} from 'react-router-dom';
import Faculdade from './faculdade';
import Curso from './curso';
import Home from './home';

export default props => (
    <BrowserRouter>
        <Switch>
            <Route path="/" exact={true} component={Home}/>
            <Route path="/faculdade" exact={true} component={Faculdade}/>
            <Route path="/curso" exact={true} component={Curso}/>
            <Redirect from="*" to="/"/>
        </Switch>
    </ BrowserRouter>
);