import {Product} from '../../types';

async function productLoader({params}: { params: { id: string } }) {
    try {
        const token = sessionStorage.getItem('token');
        const res = await fetch(`http://localhost:8080/product/${params.id}`, {
            headers: {Authorization: `Bearer ${token}`},
        });
        const comic = (await res.json()) as Product;
        return {comic};
    } catch (error) {
        return {comic: null};
    }
}

export default productLoader;
