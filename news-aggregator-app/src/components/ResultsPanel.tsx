import type { DashboardState, NewsItem } from "../types/NewsAggregator";
import AlertPanel from "./AlertPanel";
import NewsPanel from "./NewsPanel";

type ResultsPanelProps = {
  dashboardState: DashboardState;
  newsItems: NewsItem[];
};

export default function ResultsPanel({
  dashboardState,
  newsItems = [],
}: ResultsPanelProps) {
  return (
    <div>
      <AlertPanel />
      <NewsPanel newsItems={newsItems} />
    </div>
  );
}
