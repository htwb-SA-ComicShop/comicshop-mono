import { Outlet } from 'react-router';
import { AdminStatusBar, NavBar } from '../../global/components';

const NavBarWrapper = () => {
  return (
    <>
      <AdminStatusBar />
      <NavBar />
      <Outlet />
    </>
  );
};

export default NavBarWrapper;
