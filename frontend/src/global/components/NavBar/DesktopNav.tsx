import { Box, Stack } from '@chakra-ui/react';
import { ReactElement } from 'react';
import { NavLink } from 'react-router-dom';
import { NavItem } from '../../../types';

export const DesktopNav = ({ items }: { items: NavItem[] }): ReactElement => {
  return (
    <Stack direction='row' spacing={4} alignItems='center'>
      {items.map((i) => (
        <Box key={i.label}>
          <NavLink to={i.href}>{i.label}</NavLink>
        </Box>
      ))}
    </Stack>
  );
};
