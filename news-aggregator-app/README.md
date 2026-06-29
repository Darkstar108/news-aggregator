# News Aggregator App

## Overview

Single-page React with TypeScript application that is used to search for news articles and displays news items fetched via a REST API.

## Architecture and Design

### App State

News Aggregator app is a single-page app that changes what is being displayed on the basis of the dashboard state. All of the application's state apart from the query is maintained in one parent component (NewsDashboard) while the other components are presentation components that just display what they are given. The dashboard state can have the following values:

- **welcome**: Initial state of the application which shows a welcome page
- **loading**: Intermediate state of the application after the user enters their query but before the application receives a response during which a loading indicator spins
- **results**: State of the application after the user enters their query and the response is successfully recieved. The app displays the fetched news items using cards and a filtering & sort panel is made visible under the search bar.
- **cached**: State of the application after the user enters their query and a cached response is recieved. The app displays the cached news items using cards and a filtering & sort panel is made visible under the search bar.
- **mocked**: State of the application after the user enters their query and a mocked response is recieved. This can either be because the offline mode button was toggled or the API returned an error. The app displays the mocked news items using cards and a filtering & sort panel is made visible under the search bar.
- **empty**: State of the application after the user enters their query and the response is successfully recieved with no news items. An error page is displayed
- **error**: State of the application after the user enters their query and the API returns an error. An error page is displayed. However, this state is not reachable currently as the app navigates to a mocked state on error instead

### Filtering & Sorting

The app uses client-side filtering & sorting to give the user a snappy experience. The use of useMemo() ensures that the page only re-renders when required. The user can filter by sources, sentiment or source-credibilty and the sort by published date from latest or oldest.

### Offline Mode Toggle

An offline mode button is provided next to the search bar. On toggling offline mode, hardcoded mock data is fetched instead of making an API call to the microservice

### Responsive Design

The application has been developed to be viewed both on the web as well as on mobile browsers.  
The News Cards, which are the main component of the app are displayed with the article's image on the left and content on the right in the web view. While in the mobile view, the image goes on top with the content below for a slimmer card that fits narrower screens

## Local Usage

To run the app locally, first install required dependencies using

```
npm install
```

One can verify the app by building it using

```
npm run build
```

Finally to start a local dev server, use

```
npm run dev
```

## Related Documentation

- [Project Overview](../README.md)
- [Backend Documentation](../news-aggregator-microservice/README.md)
