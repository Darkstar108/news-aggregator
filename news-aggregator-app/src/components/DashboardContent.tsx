import type { DashboardState, NewsItem } from "../types/NewsAggregator";
import {
  WELCOME_MESSAGE,
  ERROR_MESSAGE,
  EMPTY_RESULTS_MESSAGE,
} from "../constants";
import InformationPanel from "./InformationPanel";
import LoadingPanel from "./LoadingPanel";
import ResultsPanel from "./ResultsPanel";
import errorImage from "../assets/error-image.png";
import welcomeImage from "../assets/welcome-image.png";
import emptyResultsImage from "../assets/empty-results-image.png";

type DashboardContentProps = {
  dashboardState: DashboardState;
  newsItems: NewsItem[];
};

export default function DashboardContent({
  dashboardState,
  newsItems = [],
}: DashboardContentProps) {
  return (
    <div className="dashboard-content">
      {dashboardState === "welcome" && (
        <InformationPanel message={WELCOME_MESSAGE} imgUrl={welcomeImage} />
      )}

      {dashboardState === "loading" && <LoadingPanel />}

      {dashboardState === "error" && (
        <InformationPanel message={ERROR_MESSAGE} imgUrl={errorImage} />
      )}

      {dashboardState === "empty" && (
        <InformationPanel
          message={EMPTY_RESULTS_MESSAGE}
          imgUrl={emptyResultsImage}
        />
      )}

      {(dashboardState === "results" || dashboardState === "degraded") && (
        <ResultsPanel dashboardState={dashboardState} newsItems={newsItems} />
      )}
    </div>
  );
}
