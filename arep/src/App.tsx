import React from 'react';
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import ScrumBudget from './pages/ScrumBudget';
import './App.css';

function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      {/* Agrega más rutas aquí según sea necesario */}
      {/* <Route path="/dashboard" element={<DashboardPage />} /> */}
      <Route path="/scrum-budget" element={<ScrumBudget />} />
    </Routes>
  );
}

export default App;
