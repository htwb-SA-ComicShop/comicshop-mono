import { useKeycloak } from '@react-keycloak/web';

export default function useAuth() {
  const { keycloak } = useKeycloak();
  const { login, logout, authenticated, tokenParsed } = keycloak;
  const email = tokenParsed?.email;
  const username = tokenParsed?.preferred_username;
  const isAdmin = tokenParsed?.realm_access?.roles.includes('shop-admin');
  const signup = () => {
    login({ action: 'register' });
  };
  return {
    isLoggedIn: authenticated,
    isAdmin,
    login,
    logout,
    email,
    username,
    signup,
  };
}
