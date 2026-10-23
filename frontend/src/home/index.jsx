import React from 'react';
import '../App.css';
import '../static/css/home/home.css'; 
import { Link } from 'react-router-dom';
import tokenService from '../services/token.service';

export default function Home(){
    const jwt = tokenService.getLocalAccessToken();
    
    return(
        <div className="home-page-container">
            <div className="hero-div">
                <h1>Your game</h1>
                <h3>---</h3>
                <h3>Do you want to play?</h3>         
                {jwt ? ( // Conditional rendering based on `jwt`
                    <>
                        <Link to="/matches/new" className="btn btn-success">Create new Chess match!</Link><br />
                        <Link to="/matches" className="btn btn-primary">Show me the available matches!</Link>
                    </>
                ) : (
                    <p>Please log in to create or view matches.</p>
                )}                 
            </div>
        </div>
    );
}