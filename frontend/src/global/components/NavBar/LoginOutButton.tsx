import { Button } from '@chakra-ui/react';
import useAuth from '../../../auth/hooks/useAuth.hook';
import { ReactElement } from 'react';

interface PropTypes {
  platform: 'desktop' | 'mobile';
}

const LogInOutButton = ({ platform }: PropTypes): ReactElement => {
  const { isLoggedIn, username, logout, login } = useAuth();
  const isDesktop = platform === 'desktop';
  return (
    <Button
      display={isDesktop ? { base: 'none', md: 'flex' } : undefined}
      onClick={() => {
        isLoggedIn ? logout() : login();
      }}
      variant={isDesktop ? 'solid' : 'unstyled'}
      color={isDesktop ? undefined : 'teal'}
    >
      {isLoggedIn ? `Logout ${username}` : 'Login'}
    </Button>
  );
};

export default LogInOutButton;
