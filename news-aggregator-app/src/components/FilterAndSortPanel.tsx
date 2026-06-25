import Autocomplete from "@mui/material/Autocomplete";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select, { type SelectChangeEvent } from "@mui/material/Select";
import TextField from "@mui/material/TextField";
import MenuItem from "@mui/material/MenuItem";
import { SortByValues, FilterByValues } from "../constants";

type FilterAndSortPanelProps = {
  sourceList: string[];
  selectedSources: string[];
  sortBy: string;
  filterBy: string;
  onSourceFilterUpdate: (selectedSources: string[]) => void;
  onSortChange: (sort: SortByValues) => void;
  onFilterChange: (filter: FilterByValues) => void;
  onReset: () => void;
};

export default function FilterAndSortPanel({
  sourceList = [],
  selectedSources = [],
  sortBy = SortByValues.LATEST,
  filterBy = "",
  onSourceFilterUpdate,
  onSortChange,
  onFilterChange,
  onReset,
}: FilterAndSortPanelProps) {
  return (
    <div className="filter-sort">
      <Autocomplete
        className="filter-source"
        multiple
        id="source-filter"
        options={sourceList}
        getOptionLabel={(option) => option}
        value={selectedSources}
        onChange={(_event: any, updatedSourceSelection: string[]) => {
          onSourceFilterUpdate(updatedSourceSelection);
        }}
        filterSelectedOptions
        size="small"
        renderInput={(params) => (
          <TextField
            {...params}
            variant="standard"
            label="Select Source"
            placeholder="Select source to filter..."
          />
        )}
      />
      <FormControl
        className="filter-sentiment-and-credibility"
        variant="outlined"
        sx={{ m: 1, minWidth: 120 }}
      >
        <InputLabel id="filter-input-label" shrink>
          Filter by
        </InputLabel>
        <Select
          labelId="filter-select-label"
          id="filter-select"
          displayEmpty
          value={filterBy}
          onChange={(event: SelectChangeEvent) => {
            onFilterChange(event.target.value as FilterByValues);
          }}
          label="Filter by"
          size="small"
        >
          <MenuItem value="" disabled style={{ display: "none" }}>
            Select Filter
          </MenuItem>
          <MenuItem value={FilterByValues.POSITIVE}>
            Positive Sentiment
          </MenuItem>
          <MenuItem value={FilterByValues.NEUTRAL}>Neutral Sentiment</MenuItem>
          <MenuItem value={FilterByValues.NEGATIVE}>
            Negative Sentiment
          </MenuItem>
          <MenuItem value={FilterByValues.HIGH}>High Credibility</MenuItem>
          <MenuItem value={FilterByValues.MEDIUM}>Medium Credibility</MenuItem>
          <MenuItem value={FilterByValues.LOW}>Low Credibility</MenuItem>
        </Select>
      </FormControl>
      <FormControl
        className="sort"
        variant="outlined"
        sx={{ m: 1, minWidth: 120 }}
      >
        <InputLabel id="sort-input-label">Sort by</InputLabel>
        <Select
          labelId="sort-select-label"
          id="sort-select"
          value={sortBy}
          onChange={(event: SelectChangeEvent) => {
            onSortChange(event.target.value as SortByValues);
          }}
          label="Sort by"
          size="small"
        >
          <MenuItem value={SortByValues.LATEST}>Latest</MenuItem>
          <MenuItem value={SortByValues.OLDEST}>Oldest</MenuItem>
        </Select>
      </FormControl>
      <Button
        className="reset-button"
        variant="contained"
        onClick={onReset}
        sx={{ width: "15%", height: 50 }}
      >
        Reset Filters
      </Button>
    </div>
  );
}
