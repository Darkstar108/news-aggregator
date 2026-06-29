import { useState, useMemo } from "react";
import type { DashboardState, NewsResponse } from "../types/NewsAggregator";
import DashboardContent from "./DashboardContent";
import { fetchNews } from "../services/NewsApi";
import HeaderBar from "./HeaderBar";
import {
  SortByValues,
  DataFreshnessIndicator,
  FilterByValues,
  CACHED_RESPONSE_ALERT,
  MOCKED_RESPONSE_ALERT,
} from "../constants";
import { mockNewsResponse } from "../services/MockData";

export default function NewsDashboard() {
  const [dashboardState, setDashboardState] =
    useState<DashboardState>("welcome");
  const [offlineMode, setOfflineMode] = useState<boolean>(false);
  const [newsResponse, setNewsResponse] = useState<NewsResponse | null>(null);
  const [sortByValue, setSortByValue] = useState<SortByValues>(
    SortByValues.LATEST,
  );
  const [filterByValue, setFilterByValue] = useState<FilterByValues | null>(
    null,
  );
  const [selectedSources, setSelectedSources] = useState<string[]>([]);

  async function handleSearch(query: string) {
    setDashboardState("loading");
    handleReset();
    if (offlineMode) {
      setMockResponse();
      return;
    }
    try {
      const response = await fetchNews(query);
      if (response == null) {
        console.error("NewsResponse is null");
        setMockResponse();
        return;
      }
      console.log(
        "Fetched NewsResponse with dataFreshnessIndicator: %s and newsItems: %s",
        response.dataFreshnessIndicator,
        response.newsItems.length,
      );
      if (response?.dataFreshnessIndicator == DataFreshnessIndicator.CACHED) {
        setDashboardState("cached");
        response.alerts.push(CACHED_RESPONSE_ALERT);
      } else {
        response.newsItems.length > 0
          ? setDashboardState("results")
          : setDashboardState("empty");
      }
      setNewsResponse(response);
    } catch (error) {
      console.error(error);
      setMockResponse();
      return;
    }
  }

  function setMockResponse() {
    mockNewsResponse.alerts.push(MOCKED_RESPONSE_ALERT);
    setNewsResponse(mockNewsResponse);
    setDashboardState("mocked");
  }

  function handleSourceFilterUpdate(sources: string[]) {
    setSelectedSources(sources);
  }

  function handleSortChange(sort: SortByValues) {
    setSortByValue(sort);
  }

  function handleFilterChange(filter: FilterByValues) {
    setFilterByValue(filter);
  }

  function handleOfflineModeChange() {
    console.log(
      "Changing offlineMode flag from %s to %s",
      offlineMode,
      !offlineMode,
    );
    setOfflineMode((offlineMode) => !offlineMode);
  }

  function handleReset() {
    setSelectedSources([]);
    setSortByValue(SortByValues.LATEST);
    setFilterByValue(null);
  }

  const filteredAndSortedNewsItems = useMemo(() => {
    if (!newsResponse) {
      return [];
    }
    let filteredAndSortedNewsItems = [...newsResponse.newsItems];
    console.log(
      "Filtering %s newsItems by filter %s and sort %s",
      filteredAndSortedNewsItems.length,
      filterByValue,
      sortByValue,
    );

    if (selectedSources.length > 0) {
      filteredAndSortedNewsItems = filteredAndSortedNewsItems.filter(
        (newsItem) => selectedSources.includes(newsItem.source),
      );
    }

    console.log(
      "After source filter: %s newsItems",
      filteredAndSortedNewsItems.length,
    );

    switch (filterByValue) {
      case FilterByValues.POSITIVE:
        filteredAndSortedNewsItems = filteredAndSortedNewsItems.filter(
          (newsItem) => newsItem.sentiment == FilterByValues.POSITIVE,
        );
        break;

      case FilterByValues.NEUTRAL:
        filteredAndSortedNewsItems = filteredAndSortedNewsItems.filter(
          (newsItem) => newsItem.sentiment == FilterByValues.NEUTRAL,
        );
        break;

      case FilterByValues.NEGATIVE:
        filteredAndSortedNewsItems = filteredAndSortedNewsItems.filter(
          (newsItem) => newsItem.sentiment == FilterByValues.NEGATIVE,
        );
        break;

      case FilterByValues.HIGH:
        filteredAndSortedNewsItems = filteredAndSortedNewsItems.filter(
          (newsItem) => newsItem.sourceCredibility == FilterByValues.HIGH,
        );
        break;

      case FilterByValues.MEDIUM:
        filteredAndSortedNewsItems = filteredAndSortedNewsItems.filter(
          (newsItem) => newsItem.sourceCredibility == FilterByValues.MEDIUM,
        );
        break;

      case FilterByValues.LOW:
        filteredAndSortedNewsItems = filteredAndSortedNewsItems.filter(
          (newsItem) => newsItem.sourceCredibility == FilterByValues.LOW,
        );
        break;
    }

    console.log(
      "After sentiment/credibility filter: %s newsItems",
      filteredAndSortedNewsItems.length,
    );

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
  }, [newsResponse, selectedSources, sortByValue, filterByValue]);

  return (
    <div>
      <HeaderBar
        dashboardState={dashboardState}
        sourceList={newsResponse == null ? [] : newsResponse.sourceList}
        selectedSources={selectedSources}
        sortBy={sortByValue}
        filterBy={filterByValue ?? ""}
        offlineMode={offlineMode}
        handleSearch={handleSearch}
        handleSourceFilterUpdate={handleSourceFilterUpdate}
        handleSortChange={handleSortChange}
        handleFilterChange={handleFilterChange}
        handleOfflineModeChange={handleOfflineModeChange}
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
