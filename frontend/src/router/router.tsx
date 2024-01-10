import { createBrowserRouter } from 'react-router-dom';
import ProductsListPage from '../product/pages/ProductsListPage';
import NavBarWrapper from './components/NavBarWrapper';
import productLoader from './loaders/productLoader';
import EditProductPage from '../product/pages/EditProductPage';
import PrivateRouteWrapper from './components/PrivateRouteWrapper';

const router = createBrowserRouter([
  {
    path: '/',
    element: <NavBarWrapper />,
    children: [
      {
        path: '/',
        element: <ProductsListPage />,
      },
      {
        path: '/edit-product/:id',
        element: (
          <PrivateRouteWrapper role='admin'>
            <EditProductPage />
          </PrivateRouteWrapper>
        ),
        // @ts-expect-error: React Router internal type issues
        loader: productLoader,
      },
    ],
  },
]);

export default router;
