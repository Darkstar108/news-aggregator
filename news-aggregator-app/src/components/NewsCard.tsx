import Card from "@mui/material/Card";
import CardActionArea from "@mui/material/CardActionArea";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Chip from "@mui/material/Chip";
import Typography from "@mui/material/Typography";
import type { NewsItem } from "../types/NewsAggregator";
import Box from "@mui/material/Box";
import fallbackNewsCardImage from "../assets/fallback-news-card-image.png";
import { Sentiment, SourceCredibility } from "../constants";

type NewsCardProps = {
  newsItem: NewsItem;
};

export default function NewsCard({ newsItem }: NewsCardProps) {
  function getChipColour(label: string) {
    if (label == Sentiment.POSITIVE || label == SourceCredibility.HIGH) {
      return "success";
    } else if (label == Sentiment.NEGATIVE || label == SourceCredibility.LOW) {
      return "error";
    } else if (label == SourceCredibility.MEDIUM) {
      return "warning";
    }
    return "default";
  }
  return (
    <Card className="news-card">
      <CardActionArea
        className="news-card-action-area"
        component="a"
        href={newsItem.url}
        target="_blank"
        rel="noopener noreferrer"
        sx={{
          display: "flex",
          flexDirection: {
            xs: "column",
            md: "row",
          },
          alignItems: "stretch",
        }}
      >
        <CardMedia
          className="news-card-image"
          component="img"
          alt="Image thumbnail for the News Article"
          image={newsItem.urlToImage ?? fallbackNewsCardImage}
          width="30%"
          sx={{
            width: {
              xs: "100%",
              md: 300,
            },
            minWidth: {
              md: 200,
            },
            height: {
              xs: 200,
              md: "auto",
            },
            maxHeight: {
              md: 250,
            },
            objectFit: "cover",
            alignSelf: "stretch",
          }}
        />
        <CardContent
          className="news-card-content"
          sx={{ display: "flex", flexDirection: "column", flex: 1 }}
        >
          <Typography
            gutterBottom
            variant="h5"
            component="div"
            sx={{ margin: "0px" }}
          >
            {newsItem.title}
          </Typography>
          <Typography variant="subtitle2" sx={{ color: "text.secondary" }}>
            {newsItem.source} | Written By: {newsItem.author ?? "Unknown"} |{" "}
            {newsItem.publishedAt.substring(0, 10)}
          </Typography>
          <Typography variant="body2" sx={{ margin: "10px 0px" }}>
            {newsItem.description}
          </Typography>
          <Box
            className="news-card-chip-container"
            sx={{ display: "flex", flexDirection: "row" }}
          >
            <Box sx={{ display: "flex", flexDirection: "row" }}>
              <Typography variant="button" sx={{ color: "text.secondary" }}>
                Source Credibility:
              </Typography>
              <Chip
                label={newsItem.sourceCredibility}
                color={getChipColour(newsItem.sourceCredibility)}
                size="small"
                sx={{ marginLeft: "5px" }}
              />
            </Box>
            <Box
              sx={{ display: "flex", flexDirection: "row", marginLeft: "20px" }}
            >
              <Typography variant="button" sx={{ color: "text.secondary" }}>
                Sentiment:
              </Typography>
              <Chip
                label={newsItem.sentiment}
                color={getChipColour(newsItem.sentiment)}
                size="small"
                sx={{ marginLeft: "5px" }}
              />
            </Box>
          </Box>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}
