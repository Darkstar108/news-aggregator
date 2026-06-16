import type { DashboardState, NewsItem } from "../types/NewsAggregator";
import AlertPanel from "./AlertPanel";
import NewsPanel from "./NewsPanel";

type ResultsPanelProps = {
  dashboardState: DashboardState;
  newsItems: NewsItem[];
  alerts: string[];
};

export default function ResultsPanel({
  dashboardState,
  newsItems = [],
  alerts = [],
}: ResultsPanelProps) {
  return (
    <div>
      <AlertPanel dashboardState={dashboardState} alerts={alerts} />
      <NewsPanel newsItems={newsItems} />
    </div>
  );
}
