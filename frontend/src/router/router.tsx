import { createBrowserRouter } from 'react-router-dom';
import ProductsListPage from '../product/pages/ProductsListPage';
import NavBarWrapper from './components/NavBarWrapper';
import productLoader from './loaders/productLoader';
import EditProductPage from '../product/pages/EditProductPage';
import PrivateRouteWrapper from './components/PrivateRouteWrapper';
import AddProductPage from '../product/pages/AddProductPage';
import CartPage from "../cart/pages/CartPage";

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
      {
        path: '/add-product',
        element: (
          <PrivateRouteWrapper role='admin'>
            <AddProductPage />
          </PrivateRouteWrapper>
        ),
      },
      {
        path: '/shopping-cart',
        element: (
            <PrivateRouteWrapper role='customer'>
              <CartPage />
            </PrivateRouteWrapper>
        )
      }
    ],
  },
]);

export default router;
