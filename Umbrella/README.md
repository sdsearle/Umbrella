# Nerdery Android NAT

This is the Base Project for Nerdery's Android NAT.
It provides a number of key pieces to help you get started on the NAT.

Nothing that we provide should be interpreted as a mandatory component;
if you want to remove, modify, or replace any of the functionality we have provided with your own implementation, you are free to do so.

# Language

The base project is written in Kotlin, you are welcome to use the language that you are most comfortable with for your portion of the NAT. Kotlin is fully interoperable with Java.

# APIs

The api package contains an ApiServicesProvider, which provides access to multiple API classes:

* WeatherApi: This is the Dark Sky API. The only implemented endpoint is /forecast.
* ZipCodeService: This service takes a US ZIP code and returns a location model containing latitude and longitude to be used for the Dark Sky request, along with location city and state.
* IconApi: This API is used to provide URLs to icons. You will send in an icon String (from ForecastCondition) - and will get the URL for the correct Icon returned. 

# Resources

We have set up a few resources for you already.
All of the icons you need should already be in the appropriate drawable folders.
The colors used to indicate that the current temperature is "warm" or "cool" are provided in colors.xml.

# Design notes

You should have received a detailed design document from your NX resource.  It explains the implementation in detail.

This [detailed design document](https://drive.google.com/file/d/1wrAapV_RnwK4pQtRnahSOPsAR5OdMdkc/view?usp=sharing) explains the implementation.

Please review it for details relating to the application design.

# Using DarkSky API

In order to use the API, you will need to create an API key, and set it in the `com.nerdery.umbrella.darkskyapikey` variable in the `gradle.properties` file.
[Dark Sky API](https://darksky.net/dev)

Still have questions? Don't hesitate to reach out to your Talent Advocate for anything you need.