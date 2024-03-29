import {createBrowserRouter} from 'react-router-dom';
import ProductsListPage from '../product/pages/ProductsListPage';
import NavBarWrapper from './components/NavBarWrapper';
import productLoader from './loaders/productLoader';
import EditProductPage from '../product/pages/EditProductPage';
import PrivateRouteWrapper from './components/PrivateRouteWrapper';
import AddProductPage from '../product/pages/AddProductPage';
import CartPage from "../cart/pages/CartPage";
import ProfilePage from "../profile/pages/ProfilePage";
import EditProfilePage from "../profile/pages/EditProfilePage";
import ProductDetailPage from "../product/pages/ProductDetailPage";

const router = createBrowserRouter([
    {
        path: '/',
        element: <NavBarWrapper/>,
        children: [
            {
                path: '/',
                element: <ProductsListPage/>,
            },
            {
                path: '/edit-product/:id',
                element: (
                    <PrivateRouteWrapper role='admin'>
                        <EditProductPage/>
                    </PrivateRouteWrapper>
                ),
                loader: productLoader,
            },
            {
                path: '/add-product',
                element: (
                    <PrivateRouteWrapper role='admin'>
                        <AddProductPage/>
                    </PrivateRouteWrapper>
                ),
            },
            {
                path: '/shopping-cart',
                element: (
                    <PrivateRouteWrapper role='customer'>
                        <CartPage/>
                    </PrivateRouteWrapper>
                )
            },
            {
                path: '/profile',
                element: (
                    <PrivateRouteWrapper role='customer'>
                        <ProfilePage/>
                    </PrivateRouteWrapper>
                )
            },
            {
                path: '/edit-profile',
                element: (
                    <PrivateRouteWrapper role='customer'>
                        <EditProfilePage/>
                    </PrivateRouteWrapper>
                )
            },
            {
                path: '/product/:id',
                element: (
                    <ProductDetailPage/>
                )
            }
        ],
    },
]);

export default router;
