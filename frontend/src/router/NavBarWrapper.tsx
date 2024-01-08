import { Outlet } from 'react-router';
import { NavBar } from '../global/components';

const NavBarWrapper = () => {
  return (
    <>
      <NavBar />
      <Outlet />
    </>
  );
};

export default NavBarWrapper;
