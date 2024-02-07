export interface NavItem {
    label: string;
    subLabel?: string;
    href: string;
}

export interface Product {
    id: string | null;
    name: string;
    author: string;
    publisher: string;
    pages: number;
    price: number;
    description: string;
    imgUrl?: string;
}

export interface Profile {
    id: string | null;
    firstname: string;
    lastname: string;
    username: string;
    imgUrl?: string;
}

export interface ProductGridProps {
    products: Product[];
}

export interface AuthStatus {
    isLoggedIn: boolean;
    isAdmin: boolean;
    username?: string;
    token?: string;
}

export interface LoginCredentials {
    username: string;
    password: string;
}

export interface Cart {
    id: string | null;
    username: string;
    cartItems: CartItem[];
    totalPrice: number;
    boughtAt?: string | null;
    isBought: boolean;
}

export interface CartItem {
    id: string | null;
    productId: string;
    name: string;
    author: string;
    price: number;
    linkToProduct: string;
    imgUrl?: string;
}

export interface CartItemGridProps {
    cartItems: CartItem[];
}

export interface TotalPrice {
    price: number
}
