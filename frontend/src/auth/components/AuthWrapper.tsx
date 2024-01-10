import { ReactElement, ReactNode } from 'react';
import useAuth from '../hooks/useAuth.hook';

interface PropTypes {
  children?: ReactNode;
  role: 'customer' | 'admin';
}

const AuthWrapper = ({ children, role }: PropTypes): ReactElement => {
  const { isLoggedIn, isAdmin } = useAuth();
  return (
    <>
      {role === 'admin'
        ? isLoggedIn && isAdmin && children
        : isLoggedIn && children}
    </>
  );
};

export default AuthWrapper;
