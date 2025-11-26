import React from 'react';
import { useAuth } from 'react-oidc-context';

const LogoutButton: React.FC = () => {
  const auth = useAuth();

  const handleLogout = async () => {
    auth.removeUser();
    const clientId = "342s18a96gl2pbaroorqh316l8";  
    const logoutUri = "http://localhost:3000";
    const cognitoDomain = "https://us-east-18mvprkbvu.auth.us-east-1.amazoncognito.com";
    window.location.href = `${cognitoDomain}/logout?client_id=${clientId}&logout_uri=${encodeURIComponent(logoutUri)}`;
  };

  return (
    <button
      onClick={handleLogout}
      style={{
        padding: '8px 14px',
        fontSize: '0.9rem',
        fontWeight: 600,
        background: '#f56565',
        color: '#fff',
        border: 'none',
        borderRadius: 6,
        cursor: 'pointer',
        boxShadow: '0 2px 6px rgba(0,0,0,0.08)',
        transition: 'all 0.2s ease-in-out'
      }}
      onMouseOver={(e) => {
        (e.currentTarget as HTMLButtonElement).style.background = '#e53e3e';
      }}
      onMouseOut={(e) => {
        (e.currentTarget as HTMLButtonElement).style.background = '#f56565';
      }}
      aria-label="Cerrar sesión"
    >
      Cerrar Sesión
    </button>
  );
};

export default LogoutButton;