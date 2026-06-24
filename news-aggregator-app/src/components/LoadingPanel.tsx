import { Box } from "@mui/material";
import { OrbitProgress } from "react-loading-indicators";

export default function LoadingPanel() {
  return (
    <Box className="loading-panel" sx={{ marginTop: "5%" }}>
      <OrbitProgress
        data-testid="loading-orbit"
        dense
        color="#32cd32"
        size="large"
        text=""
        textColor=""
      />
    </Box>
  );
}
