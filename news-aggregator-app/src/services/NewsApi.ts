import axios from "axios";
import { mockNewsResponse } from "./MockData";

export async function fetchNews(query: string) {
    const USE_MOCK_DATA = import.meta.env.VITE_USE_MOCK_DATA;
    const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

    console.log("Mock data flag = ", USE_MOCK_DATA)
    if (USE_MOCK_DATA) {
        console.log("Fetching mock data");
        await new Promise(resolve =>
            setTimeout(resolve, 1000)
        );

        return mockNewsResponse;
    }
    console.log("Fetching news articles");
    const response = await axios.get(
        `${API_BASE_URL}/api/news/search`,
        {
            params: { query }
        }
    );

    return response.data;
}