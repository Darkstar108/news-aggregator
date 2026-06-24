import { render, screen } from "@testing-library/react";
import { expect, test } from "vitest";
import DashboardContent from "../DashboardContent";
import {
  WELCOME_MESSAGE,
  EMPTY_RESULTS_MESSAGE,
  ERROR_MESSAGE,
} from "../../constants";
import "@testing-library/jest-dom";

test("returns information panel with welcome message when dashboardState is welcome", () => {
  render(
    <DashboardContent dashboardState="welcome" newsItems={[]} alerts={[]} />,
  );
  expect(screen.getByText(WELCOME_MESSAGE)).toBeInTheDocument();
});

// test("returns loading panel when dashboardState is loading", () => {
//   render(
//     <DashboardContent dashboardState="loading" newsItems={[]} alerts={[]} />,
//   );
//   expect(screen.getByTestId("loading-orbit")).toBeInTheDocument();
// });

test("returns information panel with error message when dashboardState is error", () => {
  render(
    <DashboardContent dashboardState="error" newsItems={[]} alerts={[]} />,
  );
  expect(screen.getByText(ERROR_MESSAGE)).toBeInTheDocument();
});

test("returns information panel with empty results message when dashboardState is empty", () => {
  render(
    <DashboardContent dashboardState="empty" newsItems={[]} alerts={[]} />,
  );
  expect(screen.getByText(EMPTY_RESULTS_MESSAGE)).toBeInTheDocument();
});
