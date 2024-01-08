import { SubmitHandler, useForm } from 'react-hook-form';
import ValidatedInput from './ValidatedInput';
import { Button, useToast } from '@chakra-ui/react';
import { useNavigate } from 'react-router';
import { Product } from '../../../types';

interface FormProps {
  defaults?: Product;
  method: 'POST' | 'PUT';
  id?: string;
}

const ProductForm = ({ defaults, method, id }: FormProps) => {
  const {
    handleSubmit,
    register,
    formState: { errors, isSubmitting },
  } = useForm<Product>();

  const hasErrors = Object.keys(errors).length > 0;

  const navigate = useNavigate();

  const toast = useToast();

  const url =
    method === 'POST'
      ? 'http://localhost:8080/product/'
      : `http://localhost:8080/product/${id}`;

  const onSubmit: SubmitHandler<Product> = async (data: Product) => {
    const payload: Omit<Product, 'id'> | Product = data;
    try {
      const response = await fetch(url, {
        method,
        mode: 'cors',
        cache: 'no-cache',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });
      await response.json();
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
    <form onSubmit={handleSubmit(onSubmit)}>
      <ValidatedInput
        id='title'
        label='Titel'
        errors={errors}
        defaultValue={defaults?.name}
        errorMsg={errors?.name?.message}
        registerReturn={register('name', {
          required: 'Required',
          minLength: {
            value: 3,
            message: 'Titel muss mindestens 3 Zeichen lang sein',
          },
        })}
      />
      <ValidatedInput
        id='imageSrc'
        label='Bild-Link'
        errors={errors}
        defaultValue={defaults?.imgUrl}
        errorMsg={errors?.imgUrl?.message}
        registerReturn={register('imgUrl', {
          required: 'Required',
          pattern: {
            value:
              /[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)?/gi,
            message: 'Bitte einen valide HTTP/HTTPS URL angeben',
          },
        })}
      />
      <ValidatedInput
        id='author'
        label='Author'
        errors={errors}
        defaultValue={defaults?.author}
        errorMsg={errors?.author?.message}
        registerReturn={register('author', { required: 'Required' })}
      />
      <ValidatedInput
        id='publisher'
        label='Publisher'
        errors={errors}
        defaultValue={defaults?.publisher}
        errorMsg={errors?.publisher?.message}
        registerReturn={register('publisher', { required: 'Required' })}
      />
      <ValidatedInput
        id='pages'
        label='Pages'
        errors={errors}
        type='number'
        defaultValue={defaults?.pages}
        errorMsg={errors?.pages?.message}
        registerReturn={register('pages', { required: 'Required' })}
      />
      <ValidatedInput
        id='price'
        label='Price ($)'
        errors={errors}
        type='number'
        defaultValue={defaults?.price}
        errorMsg={errors?.price?.message}
        registerReturn={register('pages', { required: 'Required' })}
      />
      <ValidatedInput
        isTextArea
        id='description'
        label='Beschreibung'
        errors={errors}
        defaultValue={defaults?.description}
        errorMsg={errors?.description?.message}
        registerReturn={register('description', {
          required: 'Required',
          minLength: {
            value: 5,
            message: 'Beschreibung muss mindestens 5 Zeichen lang sein',
          },
        })}
      />
      <Button
        colorScheme='teal'
        isLoading={isSubmitting}
        isDisabled={hasErrors}
        type='submit'
      >
        Speichern
      </Button>
      <Button
        colorScheme='teal'
        variant='outline'
        onClick={() => navigate('/')}
      >
        Zurück
      </Button>
    </form>
  );
};

export default ProductForm;
