import { Box, Typography } from "@mui/material";

type InformationPanelProps = {
  message: string;
  imgUrl?: string;
};

export default function InformationPanel({
  message = "",
  imgUrl,
}: InformationPanelProps) {
  if (!imgUrl) {
    return (
      <Box className="information-panel" sx={{ marginTop: "5%" }}>
        <Typography variant="body1">{message}</Typography>
      </Box>
    );
  } else {
    return (
      <Box className="information-panel" sx={{ marginTop: "5%" }}>
        <img src={imgUrl} alt="Image for messages: ${message}" />
        <Typography variant="body1" sx={{ marginTop: "20px" }}>
          {message}
        </Typography>
      </Box>
    );
  }
}
