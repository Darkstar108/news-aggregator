import type {
  DashboardState,
  NewsResponse,
  NewsItem,
} from "../types/NewsAggregator";
import { WELCOME_MESSAGE } from "../constants";
import InformationPanel from "./InformationPanel";
import LoadingPanel from "./LoadingPanel";
import ResultsPanel from "./ResultsPanel";

type DashboardContentProps = {
  dashboardState: DashboardState;
  newsItems: NewsItem[];
  error: string;
};

export default function DashboardContent({
  dashboardState,
  newsItems = [],
  error,
}: DashboardContentProps) {
  return (
    <div className="dashboard-content">
      {dashboardState === "welcome" && (
        <InformationPanel message={WELCOME_MESSAGE} />
      )}

      {dashboardState === "loading" && <LoadingPanel />}

      {dashboardState === "error" && <InformationPanel message={error} />}

      {dashboardState === "empty" && <InformationPanel message="Empty" />}

      {(dashboardState === "results" || dashboardState === "degraded") && (
        <ResultsPanel dashboardState={dashboardState} newsItems={newsItems} />
      )}
    </div>
  );
}
