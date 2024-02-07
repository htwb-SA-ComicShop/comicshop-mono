import {SubmitHandler, useForm} from 'react-hook-form';
import ValidatedInput from './ValidatedInput';
import {Button, Stack, useToast} from '@chakra-ui/react';
import {useNavigate} from 'react-router';
import {Product} from '../../../types';
import useAuth from '../../../auth/hooks/useAuth.hook';

interface FormProps {
    defaults?: Product;
    method: 'POST' | 'PUT';
    id?: string;
}

const ProductForm = ({defaults, method, id}: FormProps) => {
    const {
        handleSubmit,
        register,
        formState: {errors, isSubmitting},
    } = useForm<Product>();

    const {token} = useAuth();

    const hasErrors = Object.keys(errors).length > 0;

    const navigate = useNavigate();

    const toast = useToast();

    const url =
        method === 'POST'
            ? 'http://localhost:8080/add-product'
            : `http://localhost:8080/product/${id}`;

    const onSubmit: SubmitHandler<Product> = async (data: Product) => {
        const payload: Product = method === 'PUT' ? data : {...data, id: null};
        try {
            const response = await fetch(url, {
                method,
                mode: 'cors',
                cache: 'no-cache',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(payload),
            });
            if (!response.ok) {
                throw new Error('Something went wrong!');
            }
            navigate('/');
            toast({
                title: 'All right!',
                description: `Produkt wurde erfolgreich ${
                    method === 'POST' ? 'angelegt' : 'aktualisiert'
                }`,
                position: 'top',
                status: 'success',
                duration: 5000,
                isClosable: true,
            });
        } catch (error) {
            if (error instanceof Error) {
                toast({
                    title: 'Ooops!',
                    description:
                        method === 'POST'
                            ? 'Produkt konnte nicht angelegt werden!'
                            : `${payload.name} konnte nicht geändert werden!`,
                    position: 'top',
                    status: 'error',
                    duration: 5000,
                    isClosable: true,
                });
            }
        }
    };

    return (
        <Stack as='form' onSubmit={handleSubmit(onSubmit)} w='100%' spacing={4}>
            <ValidatedInput
                id='title'
                label='Title'
                defaultValue={defaults?.name}
                errorMsg={errors?.name?.message}
                registerReturn={register('name', {
                    required: 'Required',
                    minLength: {
                        value: 3,
                        message: 'Title has to be at least 3 characters long',
                    },
                })}
            />
            <ValidatedInput
                id='imageSrc'
                label='Image URL'
                defaultValue={defaults?.imgUrl}
                errorMsg={errors?.imgUrl?.message}
                registerReturn={register('imgUrl', {
                    required: 'Required',
                    pattern: {
                        value:
                            /[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)?/gi,
                        message: 'Please enter a valid HTTP/HTTPS URL',
                    },
                    maxLength: {
                        value: 250,
                        message: 'The URL has to be at most 250 characters long',
                    },
                })}
            />
            <ValidatedInput
                id='author'
                label='Author'
                defaultValue={defaults?.author}
                errorMsg={errors?.author?.message}
                registerReturn={register('author', {required: 'Required'})}
            />
            <ValidatedInput
                id='publisher'
                label='Publisher'
                defaultValue={defaults?.publisher}
                errorMsg={errors?.publisher?.message}
                registerReturn={register('publisher', {required: 'Required'})}
            />
            <ValidatedInput
                id='pages'
                label='Pages'
                type='number'
                step={1}
                defaultValue={defaults?.pages}
                errorMsg={errors?.pages?.message}
                registerReturn={register('pages', {required: 'Required'})}
            />
            <ValidatedInput
                id='price'
                label='Price ($)'
                type='number'
                step={0.01}
                defaultValue={defaults?.price}
                errorMsg={errors?.price?.message}
                registerReturn={register('price', {required: 'Required'})}
            />
            <ValidatedInput
                isTextArea
                id='description'
                label='Description'
                defaultValue={defaults?.description}
                errorMsg={errors?.description?.message}
                registerReturn={register('description', {
                    required: 'Required',
                    minLength: {
                        value: 5,
                        message: 'The description has to be at least 5 characters long',
                    },
                })}
            />
            <Button
                colorScheme='teal'
                isLoading={isSubmitting}
                isDisabled={hasErrors}
                type='submit'
            >
                Save
            </Button>
            <Button
                colorScheme='teal'
                variant='outline'
                onClick={() => navigate('/')}
            >
                Back
            </Button>
        </Stack>
    );
};

export default ProductForm;
