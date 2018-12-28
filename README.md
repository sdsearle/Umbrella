# Nerdery Android NAT

This is the Base Project for Nerdery's Android NAT.
It provides a number of key pieces to help you get started on the NAT.

Nothing that we provide should be interpreted as a mandatory component;
if you want to remove, modify, or replace any of the functionality we have
provided with your own implementation, you are free to do so.

# Language

At Nerdery we embrace the power and flexibility of the Kotlin language and 
primarily use Kotlin when developing for Android. The provided Base Project
has been written in Kotlin.

Because Kotlin is new to the industry, we ask that you use the language that 
you are most comfortable with for your portion of the NAT. Kotlin is fully 
interoperable with Java.

# APIs

The api package contains an ApiServicesProvider, which provides access to two API classes:

* WeatherApi: This is the Dark Sky API. The only implemented endpoint is /forecast.
* ZipCodeService: This service takes a US ZIP code and returns a location model containing latitude
and longitude to be used for the Dark Sky request, along with location city and state.
* IconApi: This API is used to provide icons, since Dark Sky does not provide any.

# Resources

We have set up a few resources for you already.
All of the icons you need should already be in the appropriate drawable folders.
The colors used to indicate that the current temperature is "warm" or "cool"
are provided in colors.xml.

# Design notes
The header of the application should be static and *not* collapse when
scrolling. One question that could be asked in a second interview is how might
you go about making the header collapsable. We have a standard at Nerdery
that if something changes, it should animate it. However, for the NAT we
recognize that time constraints really don't allow for much in the way of
animations. If you see a way to use a freebie animation that is easily
implemented in a couple lines or less, feel free to include it in the project.
The status bar isn't up to modern material designs standards. Please ignore
the designs in that aspect and use the system defaults.
