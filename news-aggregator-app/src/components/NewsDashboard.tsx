import { useState, useMemo } from "react";
import type { DashboardState, NewsResponse } from "../types/NewsAggregator";
import DashboardContent from "./DashboardContent";
import { fetchNews } from "../services/NewsApi";
import HeaderBar from "./HeaderBar";
import { SortByValues, DataFreshnessIndicator } from "../constants";

export default function NewsDashboard() {
  const [dashboardState, setDashboardState] =
    useState<DashboardState>("welcome");
  const [newsResponse, setNewsResponse] = useState<NewsResponse | null>(null);
  const [sortByValue, setSortByValue] = useState<SortByValues>(
    SortByValues.LATEST,
  );
  const [selectedSources, setSelectedSources] = useState<string[]>([]);

  async function handleSearch(query: string) {
    setDashboardState("loading");
    try {
      const response = await fetchNews(query);
      setNewsResponse(response);
    } catch {
      setDashboardState("error");
    } finally {
      if (
        newsResponse?.dataFreshnessIndicator == DataFreshnessIndicator.CACHED
      ) {
        setDashboardState("cached");
      } else {
        setDashboardState("results");
      }
    }
  }

  async function handleSourceFilterUpdate(sources: string[]) {
    setSelectedSources(sources);
  }

  async function handleSortChange(sort: SortByValues) {
    setSortByValue(sort);
  }

  async function handleReset() {
    setSelectedSources([]);
    setSortByValue(SortByValues.LATEST);
  }

  const filteredAndSortedNewsItems = useMemo(() => {
    if (!newsResponse) {
      return [];
    }
    let filteredAndSortedNewsItems = [...newsResponse.newsItems];

    if (selectedSources.length > 0) {
      filteredAndSortedNewsItems = filteredAndSortedNewsItems.filter(
        (newsItem) => selectedSources.includes(newsItem.source),
      );
    }

    switch (sortByValue) {
      case SortByValues.LATEST:
        filteredAndSortedNewsItems.sort(
          (a, b) => a.publishedAt.localeCompare(b.publishedAt) * -1,
        );
        break;

      case SortByValues.OLDEST:
        filteredAndSortedNewsItems.sort((a, b) =>
          a.publishedAt.localeCompare(b.publishedAt),
        );
        break;
    }

    return filteredAndSortedNewsItems;
  }, [newsResponse, selectedSources, sortByValue]);

  return (
    <div>
      <HeaderBar
        sourceList={newsResponse == null ? [] : newsResponse.sourceList}
        selectedSources={selectedSources}
        sortBy={sortByValue}
        handleSearch={handleSearch}
        handleSourceFilterUpdate={handleSourceFilterUpdate}
        handleSortChange={handleSortChange}
        handleReset={handleReset}
      />
      <DashboardContent
        dashboardState={dashboardState}
        newsItems={filteredAndSortedNewsItems}
        alerts={newsResponse?.alerts ?? []}
      />
    </div>
  );
}
