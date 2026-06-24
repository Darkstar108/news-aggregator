export const WELCOME_MESSAGE = "Welcome to NewsAggregator, a small project that fetches news articles based on the input keywords or phrases. It fetches articles using https://newsapi.org/ with the source, author, description, publication date, sentiment analysis and source credibility. Enter a query in the search bar to begin!";
export const EMPTY_RESULTS_MESSAGE = "No results found for your search query, please try again with a different query";
export const ERROR_MESSAGE = "Sorry for the unexpected error, please try again later";
export const SEARCH_HELPER_TEXT = "Search query must be at least 2 characters and at most 50 characters";
export const CACHED_RESPONSE_ALERT = "Returning cached results from recent query";
export const MOCKED_RESPONSE_ALERT = "Returning mocked results";

const DataFreshnessIndicator = {
  LIVE: "LIVE",
  CACHED: "CACHED",
  MOCKED: "MOCKED",
} as const;
type DataFreshnessIndicator = (typeof DataFreshnessIndicator)[keyof typeof DataFreshnessIndicator];
export { DataFreshnessIndicator };

const SourceCredibility = {
  HIGH: "HIGH",
  MEDIUM: "MEDIUM",
  LOW: "LOW",
} as const;
type SourceCredibility = (typeof SourceCredibility)[keyof typeof SourceCredibility];
export { SourceCredibility };

const Sentiment = {
  POSITIVE: "POSITIVE",
  NEUTRAL: "NEUTRAL",
  NEGATIVE: "NEGATIVE",
} as const;
type Sentiment = (typeof Sentiment)[keyof typeof Sentiment];
export { Sentiment };

const SortByValues = {
  LATEST: "latest",
  OLDEST: "oldest",
} as const;
type SortByValues = (typeof SortByValues)[keyof typeof SortByValues];
export { SortByValues };

const FilterByValues = {
  POSITIVE: Sentiment.POSITIVE,
  NEGATIVE: Sentiment.NEGATIVE,
  HIGH: SourceCredibility.HIGH,
  MEDIUM: SourceCredibility.MEDIUM,
  LOW: SourceCredibility.LOW
} as const;
type FilterByValues = (typeof FilterByValues)[keyof typeof FilterByValues];
export { FilterByValues };