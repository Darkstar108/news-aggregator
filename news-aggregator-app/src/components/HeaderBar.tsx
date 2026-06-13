import FilterPanel from "./FilterAndSortPanel";
import SearchBar from "./SearchBar";
import { Typography } from "@mui/material";
import { SortByValues } from "../constants";

type HeaderBarProps = {
  sourceList: string[];
  selectedSources: string[];
  sortBy: string;
  handleSearch: (query: string) => void;
  handleSourceFilterUpdate: (selectedSources: string[]) => void;
  handleSortChange: (sort: SortByValues) => void;
  handleReset: () => void;
};

export default function HeaderBar({
  sourceList = [],
  selectedSources = [],
  sortBy = SortByValues.LATEST,
  handleSearch,
  handleSourceFilterUpdate,
  handleSortChange,
  handleReset,
}: HeaderBarProps) {
  return (
    <div className="header">
      <div className="title-search">
        <div className="title">
          <h2>News Aggregator</h2>
        </div>
        <SearchBar handleSearch={handleSearch} />
      </div>
      <FilterPanel
        sourceList={sourceList}
        selectedSources={selectedSources}
        sortBy={sortBy}
        onSourceFilterUpdate={handleSourceFilterUpdate}
        onSortChange={handleSortChange}
        onReset={handleReset}
      />
    </div>
  );
}
