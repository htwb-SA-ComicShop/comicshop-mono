import {FormControl, FormErrorMessage, FormLabel, Input, Textarea,} from '@chakra-ui/react';
import {ReactElement} from 'react';
import {UseFormRegisterReturn} from 'react-hook-form';

interface ValidatedInputProps {
    label: string;
    id: string;
    registerReturn: UseFormRegisterReturn;
    defaultValue?: string | number | readonly string[] | undefined;
    errorMsg: string | undefined;
    type?: React.HTMLInputTypeAttribute;
    step?: string | number | undefined;
    isTextArea?: boolean;
}

const ValidatedInput = ({
                            label,
                            id,
                            registerReturn,
                            defaultValue,
                            errorMsg,
                            type = 'text',
                            step = undefined,
                            isTextArea = false,
                        }: ValidatedInputProps): ReactElement => {
    return (
        <FormControl isInvalid={!!errorMsg}>
            <FormLabel htmlFor={id}>{label}</FormLabel>
            {isTextArea ? (
                <Textarea
                    minH={200}
                    id={id}
                    placeholder={label}
                    defaultValue={defaultValue}
                    {...registerReturn}
                />
            ) : (
                <Input
                    type={type}
                    step={step}
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
