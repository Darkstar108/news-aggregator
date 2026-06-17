import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";

type AlertPanelProps = {
  alerts: string[];
};

export default function AlertPanel({ alerts = [] }: AlertPanelProps) {
  const alertDisplay = [];
  for (let i = 0; i < alerts.length; i++) {
    alertDisplay.push(
      <Typography key={i} variant="button" sx={{ color: "warning.main" }}>
        {alerts.at(i)}
      </Typography>,
    );
  }
  return (
    <Box sx={{ display: "flex", flexDirection: "column", margin: "10px 0px" }}>
      {alertDisplay}
    </Box>
  );
}
