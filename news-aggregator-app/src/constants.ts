export const WELCOME_MESSAGE = "Welcome to NewsAggregator";

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
  POSITIVE: "positive",
  NEGATIVE: "negative",
  HIGH_CREDIBILITY: "high_credibility",
  LOW_CREDIBILITY: "low_credibility"
} as const;
type SortByValues = (typeof SortByValues)[keyof typeof SortByValues];
export { SortByValues };