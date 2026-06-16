import type { DashboardState } from "../types/NewsAggregator";

type AlertPanelProps = {
  dashboardState: DashboardState;
  alerts: string[];
};

export default function AlertPanel({
  dashboardState,
  alerts = [],
}: AlertPanelProps) {
  if (dashboardState == "cached") {
    alerts.push("Cached result found");
  }
  return <></>;
}
