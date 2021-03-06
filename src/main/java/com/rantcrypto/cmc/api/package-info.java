/**
 * <p>Base package of the Spring Boot REST API.</p>
 * <p>This Spring Boot REST API and its developers are not directly affiliated nor endorsed by CoinMarketCap.</p>
 * <p>The CoinMarketCap API is a suite of high-performance RESTful JSON endpoints that are specifically designed to meet the mission-critical demands of application developers, data scientists, and enterprise business platforms.</p>
 * 
 * <p><b>Standards and Conventions:</b></p>
 * <p>Each HTTP request must contain the header Accept: application/json. You should also send an Accept-Encoding: deflate, gzip header to receive data fast and efficiently.</p>
 * 
 * <p><em>Endpoint Response Payload Format</em></p>
 * <p>All endpoints return data in JSON format with the results of your query under data if the call is successful.</p>
 * <p>A Status object is always included for both successful calls and failures when possible. The Status object always includes the current time on the server when the call was executed as timestamp, the number of API call credits this call utilized as credit_count, and the number of milliseconds it took to process the request as elapsed. Any details about errors encountered can be found under the error_code and error_message.</p>
 * 
 * <p><em>Cryptocurrency, Exchange, and Fiat currency identifiers</em></p>
 * <ul>
 * 	<li>Cryptocurrencies may be identified in endpoints using either the cryptocurrency's unique CoinMarketCap ID as id (eg. id=1 for Bitcoin) or the cryptocurrency's symbol (eg. symbol=BTC for Bitcoin). For a current list of supported cryptocurrencies use the /cryptocurrency/map call.</li>
 * 	<li>Exchanges may be identified in endpoints using either the exchange's unique CoinMarketCap ID as id (eg. id=270 for Binance) or the exchange's web slug (eg. slug=binance for Binance). For a current list of supported exchanges use the /exchange/map call.</li>
 * 	<li>All fiat currency options use the standard ISO 8601 currency code (eg. USD for the US Dollar). For a current list of supported fiat currencies use the /fiat/map endpoint. Unless otherwise stated, endpoints with fiat currency options like the convert parameter support these 93 major currency codes.</li>
 * </ul>
 * <p>Warning: Using CoinMarketCap IDs is always recommended as not all cryptocurrency symbols are unique. They can also change with a cryptocurrency rebrand. If a symbol is used the API will always default to the cryptocurrency with the highest market cap if there are multiple matches. The convert parameter also defaults to fiat if a cryptocurrency symbol also matches a supported fiat currency. You may use the convenient /map endpoints to quickly find the corresponding CoinMarketCap ID for a cryptocurrency or exchange.</p>
 * 
 * <p><em>Bundling API Calls</em></p>
 * <ul>
 * 	<li>Many endpoints support ID and crypto/fiat currency conversion bundling. This means you can pass multiple comma-separated values to an endpoint to query or convert several items at once. Check the id, symbol, slug, and convert query parameter descriptions in the endpoint documentation to see if this is supported for an endpoint.</li>
 * 	<li>Endpoints that support bundling return data as an object map instead of an array. Each key-value pair will use the identifier you passed in as the key.</li>
 * </ul>
 * <p>Price conversions that are returned inside endpoint responses behave in the same fashion. These are enclosed in a quote object.</p>
 * 
 * <p><em>Date and Time Formats</em></p>
 * <ul>
 * 	<li>All endpoints that require date/time parameters allow timestamps to be passed in either ISO 8601 format (eg. 2018-06-06T01:46:40Z) or in Unix time (eg. 1528249600). Timestamps that are passed in ISO 8601 format support basic and extended notations; if a timezone is not included, UTC will be the default.</li>
 * 	<li>All timestamps returned in JSON payloads are returned in UTC time using human-readable ISO 8601 format which follows this pattern: yyyy-mm-ddThh:mm:ss.mmmZ. The final .mmm designates milliseconds. Per the ISO 8601 spec the final Z is a constant that represents UTC time.</li>
 * 	<li>Data is collected, recorded, and reported in UTC time unless otherwise specified.</li>
 * </ul>
 * 
 * <p><em>Versioning</em></p>
 * <p>The CoinMarketCap API is versioned to guarantee new features and updates are non-breaking. The latest version of this API is /v1/.</p>
 * 
 * <p><b>Best Practices:</b></p>
 * 
 * <p><em>Use CoinMarketCap ID Instead of Cryptocurrency Symbol</em></p>
 * <p>Utilizing common cryptocurrency symbols to reference cryptocurrencies on the API is easy and convenient but brittle. You should know that many cryptocurrencies have the same symbol, for example, there are currently three cryptocurrencies that commonly refer to themselves by the symbol HOT. Cryptocurrency symbols also often change with cryptocurrency rebrands. When fetching cryptocurrency by a symbol that matches several active cryptocurrencies CoinMarketCap returns the one with the highest market cap at the time of the query. To ensure you always target the cryptocurrency you expect, use permanent CoinMarketCap IDs. These IDs are used reliably by numerous mission critical platforms and never change.</p>
 * <p>CoinMarketCap makes fetching a map of all active cryptocurrencies' CoinMarketCap IDs very easy. Just call CoinMarketCap /cryptocurrency/map endpoint to receive a list of all active currencies mapped to the unique id property. This map also includes other typical identifiying properties like name, symbol and platform token_address that can be cross referenced. In cryptocurrency calls you would then send, for example id=1027, instead of symbol=ETH. <b>It's strongly recommended that any production code utilize these IDs for cryptocurrencies, exchanges, and markets to future-proof your code.</b></p>
 * 
 * <p><em>Use the Right Endpoints for the Job</em></p>
 * <p>You may have noticed that /cryptocurrency/listings/latest and /cryptocurrency/quotes/latest return the same crypto data but in different formats. This is because the former is for requesting paginated and ordered lists of all cryptocurrencies while the latter is for selectively requesting only the specific cryptocurrencies you require. Many endpoints follow this pattern, allow the design of these endpoints to work for you!</p>
 * 
 * <p><em>Implement a Caching Strategy If Needed</em></p>
 * <p>There are standard legal data safeguards built into the Commercial User Terms that application developers should keep in mind. These Terms help prevent unauthorized scraping and redistributing of CMC data but are intentionally worded to allow legitimate local caching of market data to support the operation of your application. If your application has a significant user base and you are concerned with staying within the call credit and API throttling limits of your subscription plan consider implementing a data caching strategy.</p>
 * <p>For example instead of making a /cryptocurrency/quotes/latest call every time one of your application's users needs to fetch market rates for specific cryptocurrencies, you could pre-fetch and cache the latest market data for every cryptocurrency in your application's local database every 60 seconds. This would only require 1 API call, /cryptocurrency/listings/latest?limit=5000, every 60 seconds. Then, anytime one of your application's users need to load a custom list of cryptocurrencies you could simply pull this latest market data from your local cache without the overhead of additional calls. This kind of optimization is practical for customers with large, demanding user bases.</p>
 * 
 * <p><em>Code Defensively to Ensure a Robust REST API Integration</em></p>
 * <p>Whenever implementing any high availability REST API service for mission critical operations it's recommended to code defensively. Since the API is versioned, any breaking request or response format change would only be introduced through new versions of each endpoint, however existing endpoints may still introduce new convenience properties over time.</p>
 * <p>CoinMarketCap suggests these best practices:</p>
 * <ul>
 * 	<li>You should parse the API response JSON as JSON and not through a regular expression or other means to avoid brittle parsing logic.</li>
 * 	<li>Your parsing code should explicitly parse only the response properties you require to guarantee new fields that may be returned in the future are ignored.</li>
 * 	<li>You should add robust field validation to your response parsing logic. You can wrap complex field parsing, like dates, in try/catch statements to minimize the impact of unexpected parsing issues (like the unlikely return of a null value).</li>
 * 	<li>Implement a "Retry with exponential backoff" coding pattern for your REST API call logic. This means if your HTTP request happens to get rate limited (HTTP 429) or encounters an unexpected server-side condition (HTTP 5xx) your code would automatically recover and try again using an intelligent recovery scheme. You may use one of the many libraries available; for example, this one for Node or this one for Python.</li>
 * </ul>
 * 
 * <p><em>Reach Out and Upgrade Your Plan</em></p>
 * <p>If you're uncertain how to best implement the CoinMarketCap API in your application or your needs outgrow CoinMarketCap self-serve subscription tiers you can reach out to api@coinmarketcap.com. They'll review your needs and budget and may be able to tailor a custom enterprise plan that is right for you.</p>
 * 
 * <p><b>Errors and Rate Limits:</b></p>
 * 
 * <p><em>API Request Throttling</em></p>
 * <p>Use of the CoinMarketCap API is subject to API call rate limiting or "request throttling". This is the number of HTTP calls that can be made simultaneously or within the same minute with your API Key before receiving an HTTP 429 "Too Many Requests" throttling error. This limit scales with the usage tier and resets every 60 seconds. Please review CoinMarketCap Best Practices for implementation strategies that work well with rate limiting.</p>
 * 
 * <p><em>HTTP Status Codes</em></p>
 * <p>The API uses standard HTTP status codes to indicate the success or failure of an API call.</p>
 * <ul>
 * 	<li>400 (Bad Request) - The server could not process the request, likely due to an invalid argument.</li>
 * 	<li>401 (Unauthorized) - Your request lacks valid authentication credentials, likely an issue with your API Key.</li>
 * 	<li>402 (Payment Required) - Your API request was rejected due to it being a paid subscription plan with an overdue balance. Pay the balance in the Developer Portal billing tab and it will be enabled.</li>
 * 	<li>403 (Forbidden) - Your request was rejected due to a permission issue, likely a restriction on the API Key's associated service plan.</li>
 * 	<li>429 (Too Many Requests) - The API Key's rate limit was exceeded; consider slowing down your API Request frequency if this is an HTTP request throttling error. Consider upgrading your service plan if you have reached your monthly API call credit limit for the day/month.</li>
 * 	<li>500 (Internal Server Error) - An unexpected server issue was encountered.</li>
 * </ul>
 * 
 * <p><em>Error Response Codes</em></p>
 * <p>A Status object is always included in the JSON response payload for both successful calls and failures when possible. During error scenarios you may reference the error_code and error_message properties of the Status object. One of the API error codes below will be returned if applicable otherwise the HTTP status code for the general error type is returned.</p>
 * 
 * @since 1.0
 * @version 1.0
 * @author Phillip Groves
 * @author CoinMarketCap (documentation / data)
 */
package com.rantcrypto.cmc.api;