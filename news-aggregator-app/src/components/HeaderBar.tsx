import Stack from "@mui/material/Stack";
import SearchBar from "./SearchBar";
import FilterPanel from "./FilterPanel";

export default function HeaderBar() {
  return (
    <Stack
      className="header"
      direction="row"
      spacing={2}
      sx={{
        justifyContent: "space-evenly",
        alignItems: "center",
      }}
    >
      <h2 className="Title">News Aggregator</h2>
      <SearchBar></SearchBar>
      <FilterPanel></FilterPanel>
    </Stack>
  );
}
