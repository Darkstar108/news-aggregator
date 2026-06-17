import Stack from "@mui/material/Stack";
import NewsCard from "./NewsCard";
import type { NewsItem } from "../types/NewsAggregator";
import ScrollToTop from "react-scroll-to-top";

type NewsPanelProps = {
  newsItems: NewsItem[];
};

export default function NewsPanel({ newsItems = [] }: NewsPanelProps) {
  const newsCards = newsItems.map((newsItem) => (
    <NewsCard newsItem={newsItem} key={newsItem.id} />
  ));
  return (
    <div>
      <Stack direction="column" spacing={2}>
        {newsCards}
      </Stack>
      <ScrollToTop style={{ right: 30, bottom: 30 }} />
    </div>
  );
}
