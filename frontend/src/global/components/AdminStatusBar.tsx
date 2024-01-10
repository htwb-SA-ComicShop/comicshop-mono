import {
  Alert,
  AlertDescription,
  AlertIcon,
  AlertTitle,
} from '@chakra-ui/react';
import useAuth from '../../auth/hooks/useAuth.hook';
import { ReactElement } from 'react';

const AdminStatusBar = (): ReactElement => {
  const { isAdmin } = useAuth();
  return isAdmin ? (
    <Alert status='warning' position='sticky' top={0} zIndex={100}>
      <AlertIcon />
      <AlertTitle>Superpowers activated! 🚀</AlertTitle>
      <AlertDescription>You're logged in as administrator!</AlertDescription>
    </Alert>
  ) : (
    <></>
  );
};

export default AdminStatusBar;
