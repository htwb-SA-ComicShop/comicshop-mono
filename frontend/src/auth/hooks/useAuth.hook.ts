import { useKeycloak } from '@react-keycloak/web';
import { useEffect } from 'react';

export default function useAuth() {
  const { keycloak, initialized } = useKeycloak();
  const { login, logout, authenticated, tokenParsed } = keycloak;
  const email = tokenParsed?.email;
  const username = tokenParsed?.preferred_username;
  const cartId = tokenParsed?.cartId;
  const isAdmin = keycloak?.hasRealmRole("shop-admin");
  const token = keycloak?.token;
  const signup = () => {
    login({ action: 'register' });
  };

  useEffect(() => {
    if (token) {
      sessionStorage.setItem('token', token);
    }
    return () => {
      sessionStorage.removeItem('token');
    };
  }, [token]);

  return {
    isInitialized: initialized,
    isLoggedIn: authenticated,
    token,
    isAdmin,
    login,
    logout,
    email,
    username,
    cartId,
    signup,
  };
}
