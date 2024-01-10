import React from 'react';
import ReactDOM from 'react-dom/client';
import { ChakraProvider } from '@chakra-ui/react';
import theme from '../theme';
import { RouterProvider } from 'react-router-dom';
import { router } from './router';
import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from './auth/keycloak';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <ChakraProvider theme={theme}>
    <ReactKeycloakProvider authClient={keycloak}>
      <React.StrictMode>
        <RouterProvider router={router} />
      </React.StrictMode>
    </ReactKeycloakProvider>
  </ChakraProvider>
);
