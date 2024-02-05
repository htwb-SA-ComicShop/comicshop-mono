import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'http://localhost:8090/auth',
  realm: 'profile-service',
  clientId: 'comicshop-frontend',
});

export default keycloak;
