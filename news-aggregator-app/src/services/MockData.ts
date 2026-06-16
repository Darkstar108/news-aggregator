import type { NewsResponse } from "../types/NewsAggregator"

export const mockNewsResponse: NewsResponse = {
    newsItems: [
        {
            id: "1",
            title: "I went looking for the AI weed vape that gives you Bitcoin for smoking",
            description: "The crypto weed vape found me on 4/20, the high holiday of cannabis enthusiasts everywhere. It arrived over Slack with the thumbnail of a man exhaling a plume of vapor, the words \"every hit delivers Bitcoin\" emblazoned across it. It claimed to be advertising …",
            source: "The Verge",
            author: "Robert Hart",
            sentiment: "POSITIVE",
            sentimentScore: 4,
            sourceCredibility: "MEDIUM",
            publishedAt: "2026-05-29T17:09:12Z",
            url: "https://www.theverge.com/ai-artificial-intelligence/933916/ai-powered-crypto-cannabis-vape",
            urlToImage: "https://platform.theverge.com/wp-content/uploads/sites/2/2026/05/268553_We_went_looking_for_the_weed_vape_that_gives_you_Bitcoin_for_smoking_CVirginia2.jpg?quality=90&strip=all&crop=0%2C10.732984293194%2C100%2C78.534031413613&w=1200"
        },
        {
            id: "2",
            title: "Why Would Someone Publicly Burn $8 Million Worth of Bitcoin? Theories Are Flying",
            description: "And another question: How do you burn bitcoin?",
            source: "Gizmodo.com",
            author: "Kyle Torpey",
            sentiment: "NEGATIVE",
            sentimentScore: -5,
            sourceCredibility: "LOW",
            publishedAt: "2026-05-28T20:12:23Z",
            url: "https://gizmodo.com/why-would-someone-publicly-burn-8-million-worth-of-bitcoin-theories-are-flying-2000764705",
            urlToImage: "https://gizmodo.com/app/uploads/2026/05/bitcoin-fire-1200x675.jpg"
        },
        {
            id: "3",
            title: "SpaceX Holds More Bitcoin Than Previously Thought",
            description: "To the Moon.",
            source: "Gizmodo.com",
            author: "Kyle Torpey",
            sentiment: "POSITIVE",
            sentimentScore: 5,
            sourceCredibility: "LOW",
            publishedAt: "2026-05-21T20:45:52Z",
            url: "https://gizmodo.com/spacex-holds-more-bitcoin-than-previously-thought-2000762104",
            urlToImage: "https://gizmodo.com/app/uploads/2026/02/falcon-9-launch-1200x675.jpeg"
        },
        {
            id: "4",
            title: "Claude Helps Recover Locked $400K Bitcoin Wallet After 11 Years",
            description: "A Bitcoin holder reportedly recovered 5 BTC worth nearly $400,000 with the help of Anthropic's Claude. According to X user cprkrn, they changed their wallet password while \"stoned\" and forgot it, unable to regain access for more than 11 years. Tom's Hardware …",
            source: "Slashdot.org",
            author: "BeauHD",
            sentiment: "POSITIVE",
            sentimentScore: 6,
            sourceCredibility: "LOW",
            publishedAt: "2026-05-14T20:00:00Z",
            url: "https://slashdot.org/story/26/05/14/1857211/claude-helps-recover-locked-400k-bitcoin-wallet-after-11-years",
            urlToImage: "https://a.fsdn.com/sd/topics/ai_64.png"
        },
        {
            id: "5",
            title: "See inside the $5.99 million Brooklyn home whose owner is willing to trade for Anthropic stock",
            description: "A Brooklyn home seller is seeking Anthropic shares or bitcoin for a $5.99M property, reflecting AI equity's rise in real estate transactions.",
            source: "Business Insider",
            author: "Katherine Tangalakis-Lippert",
            sentiment: "NEUTRAL",
            sentimentScore: 1,
            sourceCredibility: "HIGH",
            publishedAt: "2026-06-04T09:07:01Z",
            url: "https://www.businessinsider.com/anthropic-shares-accepted-luxury-brooklyn-home-price-photos-2026-6",
            urlToImage: "https://i.insider.com/6a20ac132ab5f9757add8f5a?width=1200&format=jpeg"
        },
        {
            id: "6",
            title: "Start solo-mining Bitcoin from your desk for $60",
            description: "TL;DR: The BlockChance Bitcoin Ticket Miner is a pocket-sized standalone device that submits solo-mining lottery tickets to the Bitcoin network, giving you a legitimate shot at a full block reward for just $59.99 (reg. $149.99).\nLook, the odds of winning a Bi…",
            source: "Boing Boing",
            author: "Boing Boing's Shop",
            sentiment: "Neutral",
            sentimentScore: -1,
            sourceCredibility: "LOW",
            publishedAt: "2026-05-28T21:00:00Z",
            url: "https://boingboing.net/2026/05/28/start-solo-mining-bitcoin-from-your-desk-for-60.html",
            urlToImage: "https://i0.wp.com/boingboing.net/wp-content/uploads/2026/05/BlockChance.jpg?fit=2250%2C1500&quality=60&ssl=1"
        },
        {
            id: "7",
            title: "Why bitcoin traders are suddenly obsessed with a forgotten crypto",
            description: null,
            source: "Yahoo Entertainment",
            author: null,
            sentiment: "Neutral",
            sentimentScore: -1,
            sourceCredibility: "MEDIUM",
            publishedAt: "2026-05-15T16:24:50Z",
            url: "https://consent.yahoo.com/v2/collectConsent?sessionId=1_cc-session_b325eadb-5b7c-41da-9002-59b7941277f3",
            urlToImage: null
        }
    ],
    sourceList: [
        "Gizmodo.com",
        "The Verge",
        "Slashdot.org",
        "Boing Boing",
        "Business Insider",
        "Yahoo Entertainment"
    ],
    alerts: [
        "Breaking News Alert"
    ],
    sourceDiversityScore: 0.85,
    dataFreshnessIndicator: "LIVE"
};