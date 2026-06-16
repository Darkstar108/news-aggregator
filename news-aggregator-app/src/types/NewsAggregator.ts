export type DashboardState =
    | "welcome"
    | "loading"
    | "results"
    | "empty"
    | "error"
    | "cached";

export interface NewsResponse {
    newsItems: NewsItem[],
    sourceList: string[],
    alerts: string[],
    sourceDiversityScore: number
    dataFreshnessIndicator: string
}

export interface NewsItem {
    id: string,
    title: string,
    source: string,
    author: string | null,
    description: string | null,
    url: string,
    urlToImage: string | null,
    publishedAt: string,
    sentiment: string,
    sentimentScore?: number
    sourceCredibility: string
}