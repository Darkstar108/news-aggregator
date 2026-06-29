import FilterPanel from "./FilterAndSortPanel";
import SearchBar from "./SearchBar";
import { FilterByValues, SortByValues } from "../constants";
import ToggleButton from "@mui/material/ToggleButton";
import CheckIcon from "@mui/icons-material/Check";
import { Typography } from "@mui/material";
import type { DashboardState } from "../types/NewsAggregator";

type HeaderBarProps = {
  dashboardState: DashboardState;
  sourceList: string[];
  selectedSources: string[];
  sortBy: string;
  filterBy: string;
  offlineMode: boolean;
  handleSearch: (query: string) => void;
  handleSourceFilterUpdate: (selectedSources: string[]) => void;
  handleSortChange: (sort: SortByValues) => void;
  handleFilterChange: (filter: FilterByValues) => void;
  handleOfflineModeChange: () => void;
  handleReset: () => void;
};

export default function HeaderBar({
  dashboardState,
  sourceList = [],
  selectedSources = [],
  sortBy = SortByValues.LATEST,
  filterBy = "",
  offlineMode = false,
  handleSearch,
  handleSourceFilterUpdate,
  handleSortChange,
  handleFilterChange,
  handleOfflineModeChange,
  handleReset,
}: HeaderBarProps) {
  const filterPanelComponent = !(
    dashboardState === "results" ||
    dashboardState === "cached" ||
    dashboardState === "mocked"
  ) ? (
    <div></div>
  ) : (
    <FilterPanel
      sourceList={sourceList}
      selectedSources={selectedSources}
      sortBy={sortBy}
      filterBy={filterBy}
      onSourceFilterUpdate={handleSourceFilterUpdate}
      onSortChange={handleSortChange}
      onFilterChange={handleFilterChange}
      onReset={handleReset}
    />
  );
  return (
    <div className="header">
      <div className="title-search">
        <div className="title">
          <h2>News Aggregator</h2>
        </div>
        <SearchBar handleSearch={handleSearch} />
        <div className="offline-mode-toggle">
          <Typography
            variant="body2"
            sx={{
              color: "text.primary",
              padding: "1vw 1vw",
            }}
          >
            Offline Mode
          </Typography>
          <ToggleButton
            className="offline-mode-toggle-button"
            value="check"
            selected={offlineMode}
            onChange={handleOfflineModeChange}
            size="small"
            color="primary"
            sx={{
              "&.Mui-selected": {
                color: "black",
                backgroundColor: "rgba(233,1,48,0.3)",
              },

              "&.Mui-selected:hover": {
                backgroundColor: "rgba(233,1,48,0.5)",
              },
              maxWidth: "50px",
              width: {
                md: "8vw",
              },
              height: {
                xs: "40px",
                md: "50px",
              },
            }}
          >
            <CheckIcon />
          </ToggleButton>
        </div>
      </div>
      {filterPanelComponent}
    </div>
  );
}
