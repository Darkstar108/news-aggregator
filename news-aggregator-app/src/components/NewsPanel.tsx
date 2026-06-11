import Stack from '@mui/material/Stack';
import NewsCard from './NewsCard';

export default function NewsPanel() {
  return (
    <div>
      <Stack direction="row" spacing={2}>
        <NewsCard></NewsCard>
        <NewsCard></NewsCard>
        <NewsCard></NewsCard>
      </Stack>
    </div>
  );
}