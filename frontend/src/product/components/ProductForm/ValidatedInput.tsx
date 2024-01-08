import {
  FormControl,
  FormErrorMessage,
  FormLabel,
  Input,
  Textarea,
} from '@chakra-ui/react';
import { ReactElement } from 'react';
import { FieldErrors, UseFormRegisterReturn } from 'react-hook-form';

interface ValidatedInputProps {
  errors: FieldErrors;
  label: string;
  id: string;
  registerReturn: UseFormRegisterReturn;
  defaultValue?: string | number | readonly string[] | undefined;
  errorMsg: string | undefined;
  type?: React.HTMLInputTypeAttribute;
  isTextArea?: boolean;
}

const ValidatedInput = ({
  label,
  id,
  registerReturn,
  defaultValue,
  errorMsg,
  errors,
  type = 'text',
  isTextArea = false,
}: ValidatedInputProps): ReactElement => {
  return (
    <FormControl isInvalid={!!errors[id]}>
      <FormLabel htmlFor={id}>{label}</FormLabel>
      {isTextArea ? (
        <Textarea
          id={id}
          placeholder={label}
          defaultValue={defaultValue}
          {...registerReturn}
        />
      ) : (
        <Input
          type={type}
          id={id}
          placeholder={label}
          defaultValue={defaultValue}
          {...registerReturn}
        />
      )}
      <FormErrorMessage>{errorMsg}</FormErrorMessage>
    </FormControl>
  );
};

export default ValidatedInput;
