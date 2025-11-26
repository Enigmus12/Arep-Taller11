import React, { useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from "react-oidc-context";
import '../styles/HomePage.css';

const HomePage: React.FC = () => {
  const navigate = useNavigate();
  const auth = useAuth();
  const hasSyncedRef = useRef(false);

  // URL base del backend configurable por entorno
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080';

  // Sincroniza/crea el usuario en el backend usando el token de Cognito
  const syncUserWithBackend = async () => {
    try {
      const token = auth.user?.access_token;
      if (!token) {
        console.error('No access token available for user synchronization');
        return;
      }

      const response = await fetch(`${apiBaseUrl}/api/users`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        console.error('Failed to sync user:', response.status, response.statusText);
      } else {
        console.log('User synced with backend successfully');
        navigate('/scrum-budget');
      }
    } catch (err) {
      console.error('Error syncing user with backend:', err);
    }
  };

  // Ejecuta la sincronización apenas el usuario esté autenticado (una sola vez)
  useEffect(() => {
    if (auth.isAuthenticated && !hasSyncedRef.current) {
      hasSyncedRef.current = true;
      syncUserWithBackend();
    }
  }, [auth.isAuthenticated]);

  const handleLogin = () => {
    auth.signinRedirect();
  };

  const handleRegister = () => {
    auth.signinRedirect({
      extraQueryParams: {
        signup: 'true'
      }
    });
  };

  const handleLogout = async () => {
    auth.removeUser();
    
    const clientId = "342s18a96gl2pbaroorqh316l8";
    const logoutUri = "http://localhost:3000";
    const cognitoDomain = "https://TU_COGNITO_DOMAIN.auth.us-east-1.amazoncognito.com";
    window.location.href = `${cognitoDomain}/logout?client_id=${clientId}&logout_uri=${encodeURIComponent(logoutUri)}`;
  };

  const goToDashboard = () => {
    navigate('/dashboard');
  };
  const goToScrumBudget = () => {
    navigate('/scrum-budget');
  };

  // Mostrar estado de carga
  if (auth.isLoading) {
    return (
      <div className="home-container">
        <div className="content">
          <h1 className="title">Cargando...</h1>
          <p className="subtitle">Verificando estado de autenticación...</p>
        </div>
      </div>
    );
  }

  // Si ya está autenticado, no mostrar el panel; la redirección sucede en syncUserWithBackend
  if (auth.isAuthenticated) {
    return (
      <div className="home-container">
        <div className="content">
          <h1 className="title">Redirigiendo...</h1>
          <p className="subtitle">Preparando tu sesión</p>
        </div>
      </div>
    );
  }

  return (
    <div className="home-container">
      <div className="content">
        <h1 className="title">Bienvenido a Arep</h1>
        
        {!auth.isAuthenticated ? (
          // usuario no está autenticado
          <div className="buttons-container">
            <button 
              className="btn btn-login" 
              onClick={handleLogin}
            >
              Iniciar Sesión
            </button>
            
            <button 
              className="btn btn-register" 
              onClick={handleRegister}
            >
              Registrarse
            </button>
          </div>
        ) : null}

        {/* Authentication error */}
        {auth.error && (
          <div className="error-info">
            <h3>Error de Autenticación</h3>
            <p>{auth.error.message}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default HomePage;