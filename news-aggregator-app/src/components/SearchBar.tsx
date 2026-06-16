import { useState } from "react";
import TextField from "@mui/material/TextField";
import SearchIcon from "@mui/icons-material/Search";
import { IconButton } from "@mui/material";
import { SEARCH_HELPER_TEXT } from "../constants";

type SearchBarProps = {
  handleSearch: (query: string) => void;
};

export default function SearchBar({ handleSearch }: SearchBarProps) {
  const [query, setQuery] = useState("");
  const [isInvalidQueryFlag, setIsInvalidQueryFlag] = useState(false);

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      console.log("Submitted query:", query);
      if (query.length < 2 || query.length > 50) {
        setIsInvalidQueryFlag(true);
      } else {
        setIsInvalidQueryFlag(false);
        handleSearch(query);
      }
    }
  };

  return (
    <div className="search-bar">
      <TextField
        id="search-bar-textfield"
        label="Search"
        variant="outlined"
        placeholder="Enter a keyword or phrase to fetch news articles"
        size="small"
        fullWidth
        onChange={(e) => setQuery(e.target.value)}
        onKeyDown={handleKeyDown}
        error={isInvalidQueryFlag}
        helperText={isInvalidQueryFlag ? SEARCH_HELPER_TEXT : ""}
        sx={{ height: 10 }}
        slotProps={{
          input: {
            startAdornment: (
              <IconButton>
                <SearchIcon fontSize="small" />
              </IconButton>
            ),
          },
        }}
      />
    </div>
  );
}
