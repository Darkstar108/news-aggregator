import { useState } from "react";
import TextField from "@mui/material/TextField";
import SearchIcon from "@mui/icons-material/Search";
import { IconButton } from "@mui/material";
import { Height } from "@mui/icons-material";

type SearchBarProps = {
  handleSearch: (query: string) => void;
};

export default function SearchBar({ handleSearch }: SearchBarProps) {
  const [query, setQuery] = useState("");

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      handleSearch(query);
      console.log("Submitted value:", query);
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
