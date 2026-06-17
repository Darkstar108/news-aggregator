import type { NewsItem } from "../types/NewsAggregator";
import AlertPanel from "./AlertPanel";
import NewsPanel from "./NewsPanel";

type ResultsPanelProps = {
  newsItems: NewsItem[];
  alerts: string[];
};

export default function ResultsPanel({
  newsItems = [],
  alerts = [],
}: ResultsPanelProps) {
  return (
    <div>
      <AlertPanel alerts={alerts} />
      <NewsPanel newsItems={newsItems} />
    </div>
  );
}
