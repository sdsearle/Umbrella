# Nerdery Android NAT
This is the Base Project for The Nerdery's Android NAT.
It provides a number of key pieces to help you get started on the NAT.

Nothing that we provide should be interpreted as a mandatory component; if you want to remove, modify,
or replace any of the functionality we have provided with your own implementation, you are free to do so.

# APIs
The api package contains an ApiManager, which provides access to two API classes:
 * WeatherApi: This is the Weather Underground API. The only implemented endpoint is /conditions/hourly.
 * IconApi: This API is used to provide better icons than Weather Underground provides.

# Resources
We have set up a few resources for you already.
All of the icons you need should already be in the appropriate drawable folders.
The colors used to indicate that the current temperature is "warm" or "cool" are provided in colors.xml.