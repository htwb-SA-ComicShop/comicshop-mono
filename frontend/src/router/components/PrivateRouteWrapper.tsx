import {ReactNode} from 'react';
import useAuth from '../../auth/hooks/useAuth.hook';
import {Navigate, To} from 'react-router-dom';

interface PropTypes {
    children?: ReactNode;
    role: 'customer' | 'admin';
    redirectLink?: To;
}

const PrivateRouteWrapper = ({
                                 children,
                                 role,
                                 redirectLink = '/',
                             }: PropTypes) => {
    const {isLoggedIn, isAdmin} = useAuth();
    if (role === 'admin') {
        return isLoggedIn && isAdmin ? (
            children
        ) : (
            <Navigate to={redirectLink} replace/>
        );
    } else return isLoggedIn ? children : <Navigate to={redirectLink} replace/>;
};

export default PrivateRouteWrapper;
