import { createBrowserRouter } from 'react-router-dom';
import ProductsListPage from '../product/pages/ProductsListPage';
import NavBarWrapper from './NavBarWrapper';
import productLoader from './loaders/productLoader';
import EditProductPage from '../product/pages/EditProductPage';

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
        element: <EditProductPage />,
        // @ts-expect-error: React Router internal type issues
        loader: productLoader,
      },
    ],
  },
]);

export default router;
