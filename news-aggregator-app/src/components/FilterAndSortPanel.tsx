import Autocomplete from "@mui/material/Autocomplete";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select, { type SelectChangeEvent } from "@mui/material/Select";
import TextField from "@mui/material/TextField";
import MenuItem from "@mui/material/MenuItem";
import { SortByValues } from "../constants";

type FilterAndSortPanelProps = {
  sourceList: string[];
  selectedSources: string[];
  sortBy: string;
  onSourceFilterUpdate: (selectedSources: string[]) => void;
  onSortChange: (sort: SortByValues) => void;
  onReset: () => void;
};

export default function FilterAndSortPanel({
  sourceList = [],
  selectedSources = [],
  sortBy = SortByValues.LATEST,
  onSourceFilterUpdate,
  onSortChange,
  onReset,
}: FilterAndSortPanelProps) {
  return (
    <div className="filter-sort">
      <Autocomplete
        className="filter"
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
            label="Source Select"
            placeholder="Select source to filter..."
          />
        )}
      />
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
